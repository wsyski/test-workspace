/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React, {useState} from 'react';

import {Alert} from '../index';
import AlertConfirm from './AlertConfirm';

export interface Props {
	alerts: Alert[];
	onConfirm: (alert: Alert) => void;
}

const AlertMessages: React.FC<Props> = ({alerts, onConfirm}: Props) => {
	const [alert, setAlert] = useState<Alert>();
	const [alertKey, dispatch] = React.useReducer((c) => c + 1, 0);

	React.useEffect(() => {
		if (alerts && !!alerts.length) {
			setAlert(alerts[0]);
			dispatch();
		} else {
			setAlert(undefined);
		}
	}, [alerts]);

	if (typeof alert !== 'undefined') {
		return (
			<AlertConfirm alert={alert} key={alertKey} onConfirm={onConfirm} />
		);
	} else {
		return null;
	}
};

export default AlertMessages;
