import React, {useCallback, useContext, useState} from 'react';

import AlertActionsContext from '../contexts/AlertActionsContext';
import {AlertActions} from '../index';
import AlertUtil from '../utils/AlertUtil';

const useLocalStorage = <T>(
	key: string,
	initialValue: T
): [T, React.Dispatch<React.SetStateAction<T>>] => {
	const [storedValue, setStoredValue] = useState<T>(() => {
		try {
			const item = window.localStorage.getItem(key);

			return item ? JSON.parse(item) : initialValue;
		} catch (ex) {
			return initialValue;
		}
	});
	const alertActionsRef =
		useContext<React.RefObject<AlertActions>>(AlertActionsContext);

	const setValue: React.Dispatch<React.SetStateAction<T>> = useCallback(
		(value) => {
			try {
				const valueToStore =
					value instanceof Function ? value(storedValue) : value;
				setStoredValue(valueToStore);
				window.localStorage.setItem(key, JSON.stringify(valueToStore));
			} catch (ex) {
				alertActionsRef.current &&
					alertActionsRef.current.addAlert(
						AlertUtil.createAlert((ex as Error).message, 'danger')
					);
			}
		},
		[alertActionsRef, key, storedValue]
	);

	return [storedValue, setValue];
};

export default useLocalStorage;
