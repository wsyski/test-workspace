/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import classNames from 'classnames';
import React, {PropsWithChildren, useEffect, useState} from 'react';
import {WithTranslation, withTranslation} from 'react-i18next';

import MiscUtil from "../utils/MiscUtil";


interface Props extends WithTranslation {
	buttonClassName?: string | undefined | (string | undefined)[];
	buttonWrapperClassName?: string | undefined | (string | undefined)[];
	children: string;
	className?: string | undefined | (string | undefined)[]
	ellipsis?: string;
	isTruncate?: boolean;
	maxCharacters?: number;
}

const MAX_CHARACTERS_DEFAULT = 300;

export const ShowMoreTextWithT: React.FC<PropsWithChildren<Props>> = ({
	buttonClassName,
	buttonWrapperClassName,
	children,
	className,
	ellipsis = '\u2026',
	isTruncate = false,
	maxCharacters = MAX_CHARACTERS_DEFAULT,
	t,
}) => {
	const [isShowAll, setShowAll] = useState(false);
	const [text, setText] = useState<string>('');

	const isShort = children.length <= maxCharacters;
	useEffect(() => {
		if (maxCharacters) {
			if (isShowAll || isShort) {
				setText(children);
			} else {
				for (let i = maxCharacters; i >= 0; i--) {
					if (children.charAt(i) === ' ') {
						setText(children.substring(0, i));
						break;
					}
				}
			}
		}
	}, [children, isShowAll, isShort, maxCharacters, setText]);

	const handleClick = () => {
		setShowAll((v) => !v);
	};

	const buttonLabel = isShowAll ? t('txtShowLess') : t('txtShowMore');

	const buttonWrapperId = 'id_' + MiscUtil.randomString();
	const buttonWrapperAttributes = {
		'aria-expanded': isShort ? undefined : isShowAll,
	};

	return (
		<span className={classNames(className)}>
			{text}

			{!isShort && (
				<span
					className={classNames(buttonWrapperClassName, "arena-ellipsis-button-wrapper")}
					id={buttonWrapperId}
					{...buttonWrapperAttributes}
				>
					{!isShowAll && ellipsis}

					{!isTruncate && <ClayButton
						aria-controls={buttonWrapperId}
						className={classNames(buttonClassName, 'arena-ellipsis-button')}
						displayType="unstyled"
						onClick={handleClick}
						type="button"
					>
						{buttonLabel}
					</ClayButton>}
				</span>
			)}
		</span>
	);
};

export default withTranslation()(ShowMoreTextWithT);
