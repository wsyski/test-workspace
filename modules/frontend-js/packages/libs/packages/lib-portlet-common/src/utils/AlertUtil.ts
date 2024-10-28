import {Dispatch, SetStateAction} from 'react';

import {ALERT_ORIGIN_DEFAULT} from '../constants/PortletCommonConstants';
import {Alert, AlertActions, AlertConfirmType} from '../index';

import type {Status} from '@clayui/modal/lib/types';

export default class AlertUtil {
	static createAlert(
		message: string,
		status: Status,
		origin: string = ALERT_ORIGIN_DEFAULT,
		confirmType: AlertConfirmType = 'ToastConfirm',
		title?: string
	): Alert {
		return {
			confirmType,
			message,
			origin,
			status,
			title,
		};
	}

	static addAlert(alerts: Alert[], alert: Alert): Alert[] {
		if (alerts.every((a) => a.message !== alert.message)) {
			return [...alerts, alert];
		} else {
			return alerts;
		}
	}

	static deleteAlert(alerts: Alert[], alert: Alert): Alert[] {
		return alerts.filter((a) => a.message !== alert.message);
	}

	static getAlertActions(
		setAlerts: Dispatch<SetStateAction<Alert[]>>
	): AlertActions {
		const addAlert = (alert: Alert) => {
			setAlerts((alerts) => AlertUtil.addAlert(alerts, alert));
		};

		const deleteAlert = (alert: Alert) => {
			setAlerts((alerts) => AlertUtil.deleteAlert(alerts, alert));
		};

		const error = (message: string) => {
			addAlert(AlertUtil.createAlert(message, 'danger'));
		};

		const warning = (message: string) => {
			addAlert(AlertUtil.createAlert(message, 'warning'));
		};

		const info = (message: string) => {
			addAlert(AlertUtil.createAlert(message, 'info'));
		};

		const success = (message: string) => {
			addAlert(AlertUtil.createAlert(message, 'success'));
		};

		return {
			addAlert,
			deleteAlert,
			error,
			info,
			success,
			warning,
		} as AlertActions;
	}
}
