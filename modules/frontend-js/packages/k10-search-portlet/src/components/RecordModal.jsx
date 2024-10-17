import {LiferayUtil} from '@arena/lib-portlet-common';
import ClayModal, {useModal} from '@clayui/modal';
import PropTypes from 'prop-types';
import React from 'react';

import FormatUtil from '../utils/FormatUtil';
import RecordField from './RecordField';

const RecordModal = (props) => {
	const record = props.record;
	const {observer} = useModal({
		onClose: () => props.onClose(),
	});

	return (
		<ClayModal
			observer={observer}
			size="lg"
			spritemap={LiferayUtil.getClaySpritemap()}
			status="info"
		>
			<ClayModal.Header>{record.title_full}</ClayModal.Header>

			<ClayModal.Body>
				<RecordField id="record.author" value={record.author2} />

				<RecordField
					id="record.publishDate"
					value={record.publishDate}
				/>

				<RecordField id="record.format" value={record.format} />

				<RecordField id="record.language" value={record.language} />

				<RecordField
					id="record.illustrated"
					value={record.illustrated}
				/>

				<RecordField id="record.collection" value={record.collection} />

				<RecordField id="record.issn" value={record.issn} />

				<RecordField id="record.abstract" value={record.abstract} />

				{FormatUtil.formatFieldValueAsUrl(record.url)}
			</ClayModal.Body>
		</ClayModal>
	);
};

export default RecordModal;

RecordModal.propTypes = {
	record: PropTypes.any.isRequired,
};
