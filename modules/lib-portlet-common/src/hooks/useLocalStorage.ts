import React, {useCallback, useState} from 'react';

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

	const setValue: React.Dispatch<React.SetStateAction<T>> = useCallback(
		(value) => {
				const valueToStore =
					value instanceof Function ? value(storedValue) : value;
				setStoredValue(valueToStore);
				window.localStorage.setItem(key, JSON.stringify(valueToStore));

		},
		[key, storedValue]
	);

	return [storedValue, setValue];
};

export default useLocalStorage;
