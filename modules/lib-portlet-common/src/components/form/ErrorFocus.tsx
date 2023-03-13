import { FormikContextType, FormikValues, connect, getIn } from "formik";
import React, { ComponentType, Fragment, useLayoutEffect } from "react";

const formikConnect = <C extends ComponentType<{formik: any}>>(component: C) => {
	type GetProps<T> = T extends ComponentType<infer P> ? P : never;

	return connect(component as any) as ComponentType<
		Omit<GetProps<C>, 'formik'>
		>;
}

const OBJECT_DELIMITERS = '.';
const ARRAY_START_DELIMITER = '[';
const ARRAY_END_DELIMITER = ']';

function flattenToLodashFormat(target: {}): {} {
	const output = {} as {[key: string]: string};

	function step(value: any, prev = '') {
		const type = Object.prototype.toString.call(value);
		const isObject =
			type === '[object Object]' || type === '[object Array]';
		const isArray = Array.isArray(value);

		if (!isArray && isObject) {
			Object.keys(value).forEach((key: string) => {
				const newKey = prev ? prev + OBJECT_DELIMITERS + key : key;

				step(value[key], newKey);
			});
		} else if (isArray) {
			value.forEach((arrayValue: any, index: number) => {
				const arrayKey =
					prev + ARRAY_START_DELIMITER + index + ARRAY_END_DELIMITER;

				step(arrayValue, arrayKey);
			});
		} else {
			output[prev] = value;
		}
	}

	step(target);

	return output;
}

/**
 * This component focus the first error in the Formik form after the validation.
 * Note: The first is not necessary the first on the screen, it's just the first
 * key in the touched object, order is not guaranteed.
 * */

interface ErrorFocusProps {

	/**
	 * Values from Formik provider.
	 */
	formik: Pick<
		FormikContextType<FormikValues>,
		'isSubmitting' | 'touched' | 'isValidating' | 'errors'
	>;

	/**
	 * Time in ms to execute the focus in the component with the error, by default 100ms.
	 */
	focusDelay?: number;
	isTouched?: boolean;
	onFocus?: () => void;
}

const ErrorFocus=({
	focusDelay = 100,
	formik: {errors, isSubmitting, isValidating, touched},
	isTouched = true,
	onFocus,
}: ErrorFocusProps) => {
	useLayoutEffect(() => {
		if (isSubmitting && !isValidating) {
			// Flatten touched as a way to check nested keys easily and
			// fix the depth problem when the error text is created
			// by a React component like react-intl.

			const flattedFields = isTouched
				? flattenToLodashFormat(touched)
				: flattenToLodashFormat(errors);

			const errorNames = Object.keys(flattedFields).reduce(
				(prev, key) => {
					if (getIn(errors, key)) {
						prev.push(key);
					}

					return prev;
				},
				[] as string[]
			);

			if (errorNames.length && typeof document !== 'undefined') {
				let errorElement: HTMLElement | null;

				errorNames.forEach((errorKey) => {
					const selector = `[name="${errorKey}"]`;
					if (!errorElement) {
						errorElement = document.querySelector(selector);

						return;
					}
				});

				// This is to avoid the other components autofocus when submitting

				setTimeout(() => {
					errorElement && errorElement.focus();
					onFocus && onFocus();
				}, focusDelay);
			}
		}
	}, [
		isSubmitting,
		isValidating,
		isTouched,
		errors,
		touched,
		focusDelay,
		onFocus,
	]);

	return <Fragment />;
}

export default formikConnect(ErrorFocus);
