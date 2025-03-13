import {LocalizationParams} from '../index';

export const HTTP_STATUS_BAD_REQUEST = 400;
export const HEADER_ACCEPT_LANGUAGE = 'Accept-Language';
export const HEADER_CONTENT_TYPE = 'Content-Type';
export const HEADER_AUTHORIZATION = 'Authorization';

export const ALERT_ORIGIN_DEFAULT = 'global';
export const ERRORS_PREFIX = 'errors.';
export const PROJECT_VERSION = '5.8.0';

export const LOCALIZATION_PARAMS_DEFAULT = {
	dateFormat: {
		DAYS: 'yyyy-MM-dd',
		MONTHS: 'yyyy-MM',
		SECONDS: 'yyyy-MM-dd HH:mm',
		YEARS: 'yyyy',
	},
	firstDayOfWeek: 1,
	months: [
		'January',
		'February',
		'March',
		'April',
		'May',
		'June',
		'July',
		'August',
		'September',
		'October',
		'November',
		'December',
	],
	weekdaysShort: ['S', 'M', 'T', 'W', 'T', 'F', 'S'],
} as LocalizationParams;
