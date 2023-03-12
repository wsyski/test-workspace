import ClayLoadingIndicator from '@clayui/loading-indicator';
import React, {Suspense, lazy} from 'react';

import useLoader from './useLoader';

export default function useLazy(hideLoading) {
	const load = useLoader();

	return ({module, props}) => {
		const Component = lazy(() => load(module));

		return (
			<Suspense fallback={hideLoading ? <></> : <ClayLoadingIndicator />}>
				<Component {...props} />
			</Suspense>
		);
	};
}
