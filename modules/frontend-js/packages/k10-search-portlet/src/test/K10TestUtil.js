import {LIFERAY_PARAMS_DEFAULT} from '@arena/lib-portlet-common';

import {SEARCH_CONTEXT_DEFAULT} from '../contexts/SearchContext';

class K10TestUtil {
	static getSearchContext(q, searchResult) {
		return {
			...SEARCH_CONTEXT_DEFAULT,
			hitCount: searchResult.numFound,
			hits: searchResult.docs,
			q,
			search: () => {},
		};
	}

	static getLiferayParamsContext(viewMode) {
		return {
			...LIFERAY_PARAMS_DEFAULT,
			configuration: {
				...LIFERAY_PARAMS_DEFAULT.configuration,
				portletInstance: {
					...LIFERAY_PARAMS_DEFAULT.configuration.portletInstance,
					viewMode,
				},
			},
		};
	}
}

export default K10TestUtil;
