/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React from 'react';

import {Alert, AlertConfirmType} from '../index';
import AlertModalConfirm from './AlertModalConfirm';
import AlertModalConfirmCancel from "./AlertModalConfirmCancel";
import AlertToastConfirm from './AlertToastConfirm';

export interface AlertConfirmProps {
	alert: Alert;
	onConfirm: (alert: Alert) => void;
}

const Components: Record<AlertConfirmType, React.FC<AlertConfirmProps>> = {
	ModalConfirm: AlertModalConfirm,
	ModalConfirmCancel: AlertModalConfirmCancel,
	ToastConfirm: AlertToastConfirm,
};

const AlertConfirm: React.FC<AlertConfirmProps> = ({
	alert,
	onConfirm,
}: AlertConfirmProps) => {
	const component = Components[alert.confirmType];
	if (typeof component !== 'undefined') {
		return React.createElement(component, {
			alert,
			onConfirm,
		});
	} else {
		return null;
	}
};
export default AlertConfirm;
