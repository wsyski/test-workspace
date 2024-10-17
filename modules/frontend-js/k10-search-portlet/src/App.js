import ClayLoadingIndicator from '@clayui/loading-indicator';
import React, {Suspense, useReducer} from 'react';

import {SearchContext} from './contexts/SearchContext';
import {SEARCH_INIT_STATE, searchReducer} from './reducers/searchReducer';

const App = (props) => {
	const [searchState, dispatch] = useReducer(
		searchReducer,
		SEARCH_INIT_STATE
	);

	return (
		<SearchContext.Provider
			value={{
				dispatch,
				state: searchState,
			}}
		>
			<Suspense fallback={<ClayLoadingIndicator />}>
				{props.children}
			</Suspense>
		</SearchContext.Provider>
	);
};

export default App;
