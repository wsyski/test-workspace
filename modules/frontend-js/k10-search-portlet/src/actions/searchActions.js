import RecordService from '../services/RecordService';

export const searchActionType = {
	RESET: 'RESET',
	SEARCH_INIT: 'SEARCH_INIT',
	SEARCH_RESULT: 'SEARCH_RESULT',
};

export const createActionReset = () => {
	return {
		type: searchActionType.RESET,
	};
};

export const createActionSearchInit = () => {
	return {
		type: searchActionType.SEARCH_INIT,
	};
};

export const createActionSearchResult = (q, start, searchResult) => {
	return {
		payload: {
			q,
			searchResult,
			start,
		},
		type: searchActionType.SEARCH_RESULT,
	};
};

export const dispatchSearch = (
	q,
	baseUrl,
	collection,
	pageSize,
	start,
	recordFields
) => {
	return function (dispatch) {
		if (q) {
			dispatch(createActionSearchInit());
			RecordService.searchRecords(
				q,
				baseUrl,
				collection,
				pageSize,
				start,
				recordFields
			).then((searchResult) => {
				dispatch(createActionSearchResult(q, start, searchResult));
			}).catch(ex => {
				console.error(ex.message());
			});
		} else {
			dispatch(createActionReset());
		}
	};
};
