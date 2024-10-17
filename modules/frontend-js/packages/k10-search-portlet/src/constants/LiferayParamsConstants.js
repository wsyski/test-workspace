export const VIEW_MODE_FULL = 'FULL';
export const VIEW_MODE_BRIEF = 'BRIEF';

export const BASE_URL = 'baseUrl';
export const SEARCH_PAGE = 'searchPage';
export const SEARCH_QUERY_PARAMETER = 'searchQueryParameter';
export const COLLECTION = 'collection';
export const PAGE_SIZE = 'pageSize';
export const VIEW_MODE = 'viewMode';

export const PORTLET_INSTANCE_DEFAULT = {
	[BASE_URL]: 'https://bookit.axiell.com/k10/index/axiell',
	[COLLECTION]: [
		'medline',
		'NL',
		'JSTOR',
		'springer',
		'DOAJ',
		'elsevier',
		'CTG',
	],
	[PAGE_SIZE]: 8,
	[SEARCH_PAGE]: 'k10-search',
	[SEARCH_QUERY_PARAMETER]: 'p_r_p_arena_urn:arena_search_query',
	[VIEW_MODE]: VIEW_MODE_FULL,
};
