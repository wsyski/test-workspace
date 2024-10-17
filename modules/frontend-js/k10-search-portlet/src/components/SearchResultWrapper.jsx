import {LiferayUtil, useLiferayParams} from '@arena/lib-portlet-common';
import React, {useContext} from 'react';
import {withTranslation} from 'react-i18next';
import {useLocation} from 'react-router';

import * as LiferayParamsConstants from '../constants/LiferayParamsConstants';
import {SearchContext} from '../contexts/SearchContext';
import useSearch from '../hooks/useSearch';
import SearchResult from './SearchResult';

export const SearchResultWrapper = ({t}) => {
	let q;
	let start;
	const searchContext = useContext(SearchContext);
	const liferayParams = useLiferayParams();
	const viewMode = liferayParams.getInstanceValueAsString(
		LiferayParamsConstants.VIEW_MODE
	);
	const searchPage = liferayParams.getInstanceValueAsString(
		LiferayParamsConstants.SEARCH_PAGE
	);

	const location = useLocation();
	const locationSearch = location.search;
	if (viewMode === LiferayParamsConstants.VIEW_MODE_FULL) {
		const routerQuery = new URLSearchParams(locationSearch);
		q = routerQuery.has('q') ? routerQuery.get('q') : '';
		start = routerQuery.has('start') ? routerQuery.get('start') : 0;
	} else {
		const locationQuery = new URLSearchParams(window.location.search);
		const searchQueryParameter = liferayParams.getInstanceValueAsString(
			LiferayParamsConstants.SEARCH_QUERY_PARAMETER
		);
		q = locationQuery.has(searchQueryParameter)
			? locationQuery.get(searchQueryParameter)
			: '';
		start = 0;
	}
	useSearch(q, start);

	// console.log(`start: ${start} q: ${q}`);

	const getSearchPage = () => {
		const urlSearchParams = new URLSearchParams();
		urlSearchParams.append('q', searchContext.state.q);
		const locationDescriptionObject = {
			pathname: '/',
			search: `?${urlSearchParams.toString()}`,
		};
		const pagePath = LiferayUtil.getPagePath(searchPage);

		return `${pagePath}#${locationDescriptionObject.pathname}${locationDescriptionObject.search}`;
	};

	const isNonemptyHits = () => {
		return searchContext.state.hitCount && searchContext.state.hitCount > 1;
	};

	const isQueryReady = () => {
		return q && q !== '' && !searchContext.state.isLoading;
	};

	const Title = ({t}) => {
		if (isQueryReady()) {
			if (isNonemptyHits()) {
				return (
					<h2
						className="k10-search-title-nonempty"
						data-test-id="title-nonempty"
					>
						{t(`title.nonempty.${viewMode}`, {
							hitCount: searchContext.state.hitCount,
						})}
					</h2>
				);
			} else {
				return (
					<h2
						className="k10-search-title-empty"
						data-test-id="title-empty"
					>
						{t('title.empty', {query: q})}
					</h2>
				);
			}
		} else {
			return <></>;
		}
	};

	const Body = ({t}) => {
		if (isNonemptyHits()) {
			if (viewMode === LiferayParamsConstants.VIEW_MODE_FULL) {
				return <SearchResult />;
			} else {
				const searchPage = getSearchPage();

				return (
					<a
						className="k10-search-all-link"
						data-test-id="lnk-search-result"
						href={searchPage}
						title={t('lnkSearchResult.title')}
					>
						{t('lnkSearchResult.label')}
					</a>
				);
			}
		} else {
			return <></>;
		}
	};

	return (
		<>
			<Title t={t} />
			<Body t={t} />
		</>
	);
};

export default withTranslation()(SearchResultWrapper);

SearchResultWrapper.propTypes = {};
