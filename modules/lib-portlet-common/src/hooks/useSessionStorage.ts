import React, {useState} from 'react';

const useSessionStorage = <T>(
	key: string,
	initialValue: T
): [T, React.Dispatch<React.SetStateAction<T>>] => {
	const [storedValue, setStoredValue] = useState<T>(() => {
		try {
			const item = window.sessionStorage.getItem(key);

			return item ? JSON.parse(item) : initialValue;
		} catch (ex) {
			return initialValue;
		}
	});

	const setValue: React.Dispatch<React.SetStateAction<T>> = (value) => {
		try {
			const valueToStore =
				value instanceof Function ? value(storedValue) : value;
			setStoredValue(valueToStore);
			window.sessionStorage.setItem(key, JSON.stringify(valueToStore));
		} catch (ex) {
			console.error(ex);
		}
	};

	return [storedValue, setValue];
};

export default useSessionStorage;
