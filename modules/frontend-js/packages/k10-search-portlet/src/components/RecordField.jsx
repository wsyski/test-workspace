import PropTypes from 'prop-types';
import React from 'react';
import {withTranslation} from 'react-i18next';

import FormatUtil from '../utils/FormatUtil';

export const RecordField = ({id, t, value}) => {
	return (
		<>
			{value && (
				<>
					<span className="field-name" data-testid="field-name">
						{t(id)}
					</span>
					:&nbsp;
					<span className="field-value" data-testid="field-value">
						{FormatUtil.formatFieldValueAsString(value)}
					</span>
					<br />
				</>
			)}
		</>
	);
};

export default withTranslation()(RecordField);

RecordField.propTypes = {
	id: PropTypes.string.isRequired,
	value: PropTypes.oneOfType([
		PropTypes.string,
		PropTypes.arrayOf(PropTypes.string),
	]),
};
