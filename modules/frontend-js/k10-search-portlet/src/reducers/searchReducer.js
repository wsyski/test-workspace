import {searchActionType} from '../actions/searchActions';

export const SEARCH_INIT_STATE = {
	hitCount: null,
	hits: [],
	isLoading: false,
	q: '',
	start: 0,
};

export const searchReducer = (state = SEARCH_INIT_STATE, action) => {
	switch (action.type) {
		case searchActionType.SEARCH_INIT:
			return {
				...state,
				isLoading: true,
			};
		case searchActionType.SEARCH_RESULT:
			return {
				...state,
				hitCount: action.payload.searchResult.hitCount,
				hits: action.payload.searchResult.records,
				isLoading: false,
				q: action.payload.q,
				start: action.payload.start,
			};
		case searchActionType.RESET:
			return {
				...state,
				...SEARCH_INIT_STATE,
			};
		default:
			return state;
	}
};
