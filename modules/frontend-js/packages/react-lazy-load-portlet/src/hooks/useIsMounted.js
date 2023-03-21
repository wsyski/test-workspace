import {useCallback, useLayoutEffect, useRef} from 'react';

/**
 * Hook for determining whether a component is still mounted.
 *
 * Use this to guard side-effects of asynchronous operations (fetches,
 * promises) that may complete after a component has been unmounted.
 *
 * Example:
 *
 *      const isMounted = useIsMounted();
 *      const [value, setHidden] = useHidden(true);
 *
 *      setTimeout(() => {
 *          if (isMounted()) {
 *              setHidden(true);
 *          }
 *      }, 1000);
 *
 */
export default function useIsMounted() {
	const mountedRef = useRef(false);
	const isMounted = useCallback(() => mountedRef.current, []);

	useLayoutEffect(() => {
		mountedRef.current = true;

		return () => {
			mountedRef.current = false;
		};
	}, []);

	return isMounted;
}
