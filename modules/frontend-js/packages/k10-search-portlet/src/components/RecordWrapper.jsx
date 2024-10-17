import {useLiferayParams} from '@arena/lib-portlet-common';
import PropTypes from 'prop-types';
import React from 'react';

import * as LiferayParamsConstants from '../constants/LiferayParamsConstants';
import useRecord from '../hooks/useRecord';
import Record from './Record';
import RecordModal from './RecordModal';

const RecordWrapper = (props) => {
	const isModal = props.location.state && props.location.state.isModal;
	const id = props.id;
	const liferayParams = useLiferayParams();
	const collection = liferayParams.getInstanceValueAsArray(
		LiferayParamsConstants.COLLECTION
	);
	const baseUrl = liferayParams.getInstanceValueAsString(
		LiferayParamsConstants.BASE_URL
	);
	const record = useRecord(id, baseUrl, collection);

	if (isModal) {
		return (
			<RecordModal
				onClose={() => props.history.goBack()}
				record={record}
			/>
		);
	} else {
		return <Record record={record} />;
	}
};

export default RecordWrapper;

RecordWrapper.propTypes = {
	id: PropTypes.string.isRequired,
};
