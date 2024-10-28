import {LiferayUtil, useLiferayParams} from '@arena/lib-portlet-common';
import ClayCard from '@clayui/card';
import ClayIcon from '@clayui/icon';
import ClayPagination from '@clayui/pagination';
import classNames from "classnames";
import React, {useContext} from 'react';
import {withTranslation} from 'react-i18next';
import {useHistory} from 'react-router';
import {Link} from 'react-router-dom';

import * as LiferayParamsConstants from '../constants/LiferayParamsConstants';
import {SearchContext} from '../contexts/SearchContext';
import {HIT_COUNT_MAX} from '../services/RecordService';
import FormatUtil from '../utils/FormatUtil';
import RecordField from './RecordField';

export const SearchResult = ({t}) => {
	const history = useHistory();
	const searchContext = useContext(SearchContext);
	const liferayParams = useLiferayParams();
	const pageSize = liferayParams.getInstanceValueAsNumber(
		LiferayParamsConstants.PAGE_SIZE
	);
	const totalPages = Math.ceil(
		Math.min(searchContext.state.hitCount, HIT_COUNT_MAX) / pageSize
	);

	const start2Page = (s) => {
		return s / pageSize + 1;
	};
	const getPage = () => {
		return start2Page(searchContext.state.start);
	};

	const goToPage = (page) => {
		const start = (page - 1) * pageSize;
		if (start !== searchContext.state.start) {
			history.push({
				pathname: '',
				search: `?q=${encodeURIComponent(
					searchContext.state.q
				)}&start=${encodeURIComponent(start)}`,
			});
		}
	};

	return (
		<>
			<div className={classNames("k10-search-result-container")}>
				{searchContext.state.hits.map((record, i) => {
					return (
						<React.Fragment key={i}>
							<ClayCard border="primary">
								<ClayCard.Caption>
									<h3>
										<Link
											title={t('lnkRecord.title')}
											to={{
												pathname: `/record/${record.id}`,
												state: {isModal: true},
											}}
										>
											{FormatUtil.formatFieldValueAsString(
												record.title
											)}
										</Link>
									</h3>
								</ClayCard.Caption>

								<ClayCard.Body>
									<ClayCard.Row>
										<RecordField
											id="record.author2"
											value={record.author2}
										/>
									</ClayCard.Row>

									<ClayCard.Row>
										<RecordField
											id="record.publishDate"
											value={record.publishDate}
										/>
									</ClayCard.Row>

									<ClayCard.Row>
										<RecordField
											id="record.format"
											value={record.format}
										/>
									</ClayCard.Row>

									<ClayCard.Row>
										<RecordField
											id="record.language"
											value={record.language}
										/>
									</ClayCard.Row>
								</ClayCard.Body>
							</ClayCard>
						</React.Fragment>
					);
				}, this)}
			</div>

			<div>
				<ClayPagination>
					<ClayPagination.Item
						disabled={getPage() <= 1}
						onClick={() => goToPage(getPage() - 1)}
					>
						<ClayIcon
							spritemap={LiferayUtil.getClaySpritemap()}
							symbol="angle-left"
						/>
					</ClayPagination.Item>

					<ClayPagination.Item
						disabled={getPage() >= totalPages}
						onClick={() => goToPage(getPage() + 1)}
					>
						<ClayIcon
							spritemap={LiferayUtil.getClaySpritemap()}
							symbol="angle-right"
						/>
					</ClayPagination.Item>
				</ClayPagination>
			</div>
		</>
	);
};

export default withTranslation()(SearchResult);

SearchResult.propTypes = {};
