import {
	createActionReset,
	createActionSearchResult,
} from '../../actions/searchActions';
import {SEARCH_INIT_STATE, searchReducer} from '../searchReducer';

describe('searchReducer', () => {
	test('returns default initial state when no action is passed', () => {
		const state = searchReducer(undefined, {});
		expect(state).toEqual(SEARCH_INIT_STATE);
	});

	test('returns state after RESET action', () => {
		const state = searchReducer(undefined, createActionReset());
		expect(state).toEqual(SEARCH_INIT_STATE);
	});

	test('returns state after SEARCH_RESULT action`', () => {
		const state = searchReducer(
			undefined,
			createActionSearchResult('query', 0, {
				hitCount: 1,
				records: [{}],
				start: 0,
			})
		);
		expect(state).toEqual({
			hitCount: 1,
			hits: [{}],
			isLoading: false,
			q: 'query',
			start: 0,
		});
	});
});
