import ClayCard from '@clayui/card';
import PropTypes from 'prop-types';
import React from 'react';

import FormatUtil from '../utils/FormatUtil';
import RecordField from './RecordField';

const Record = (props) => {
	const record = props.record;

	return (
		<ClayCard>
			<ClayCard.Caption>
				<h3>
					{FormatUtil.formatFieldValueAsString(record.title_full)}
				</h3>
			</ClayCard.Caption>

			<ClayCard.Body>
				<ClayCard.Row>
					<RecordField id="record.author" value={record.author2} />
				</ClayCard.Row>

				<ClayCard.Row>
					<RecordField
						id="record.publishDate"
						value={record.publishDate}
					/>
				</ClayCard.Row>

				<ClayCard.Row>
					<RecordField id="record.format" value={record.format} />
				</ClayCard.Row>

				<ClayCard.Row>
					<RecordField id="record.language" value={record.language} />
				</ClayCard.Row>

				<ClayCard.Row>
					<RecordField
						id="record.illustrated"
						value={record.illustrated}
					/>
				</ClayCard.Row>

				<ClayCard.Row>
					<RecordField
						id="record.collection"
						value={record.collection}
					/>
				</ClayCard.Row>

				<ClayCard.Row>
					<RecordField id="record.issn" value={record.issn} />
				</ClayCard.Row>

				<ClayCard.Row>
					<RecordField id="record.abstract" value={record.abstract} />
				</ClayCard.Row>

				{FormatUtil.formatFieldValueAsUrl(record.url)}
			</ClayCard.Body>
		</ClayCard>
	);
};

export default Record;

Record.propTypes = {
	record: PropTypes.any.isRequired,
};
