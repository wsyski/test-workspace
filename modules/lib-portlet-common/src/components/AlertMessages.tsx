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
