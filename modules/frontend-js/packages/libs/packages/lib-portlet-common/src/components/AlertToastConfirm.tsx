import ClayAlert from '@clayui/alert';
import React from 'react';
import {WithTranslation, withTranslation} from 'react-i18next';

import LiferayUtil from '../utils/LiferayUtil';
import {AlertConfirmProps} from './AlertConfirm';

const AUTO_CLOSE_TIMEOUT = 8000;

export interface Props extends WithTranslation, AlertConfirmProps {}

const AlertToastConfirmWithT: React.FC<Props> = ({
	alert,
	onConfirm,
}: Props) => {
	// console.log('Toast alert:', alert);

	const onClose = () => {
		// console.log('Deleting alert:', alert);

		onConfirm(alert);
	};

	return (
		<ClayAlert.ToastContainer>
			<ClayAlert
				autoClose={AUTO_CLOSE_TIMEOUT}
				displayType={alert.displayType}
				onClose={onClose}
				spritemap={LiferayUtil.getClaySpritemap()}
				title={alert?.title}
			>
				{alert.message}
			</ClayAlert>
		</ClayAlert.ToastContainer>
	);
};

export default withTranslation()(
	AlertToastConfirmWithT
) as React.FC<AlertConfirmProps>;
