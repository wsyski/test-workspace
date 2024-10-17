import {useLiferayParams} from '@arena/lib-portlet-common';
import {useContext, useEffect} from 'react';

import {dispatchSearch} from '../actions/searchActions';
import * as LiferayParamsConstants from '../constants/LiferayParamsConstants';
import {SearchContext} from '../contexts/SearchContext';

const RECORD_FIELDS =
	'id,title,abstract,url,format,language,publishDate,author2';

const useSearch = (q, start) => {
	const searchContext = useContext(SearchContext);
	const liferayParams = useLiferayParams();
	const baseUrl = liferayParams.getInstanceValueAsString(
		LiferayParamsConstants.BASE_URL
	);
	const pageSize = liferayParams.getInstanceValueAsNumber(
		LiferayParamsConstants.PAGE_SIZE
	);
	const collection = liferayParams.getInstanceValueAsArray(
		LiferayParamsConstants.COLLECTION
	);
	const dispatch = searchContext.dispatch;

	useEffect(() => {
		dispatchSearch(
			q,
			baseUrl,
			collection,
			pageSize,
			start,
			RECORD_FIELDS
		)(dispatch);
	}, [q, baseUrl, collection, pageSize, start, dispatch]);
};

export default useSearch;
