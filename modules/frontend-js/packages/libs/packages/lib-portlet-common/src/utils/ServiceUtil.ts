import Axios, {AxiosRequestConfig, CancelTokenSource} from 'axios';

import {
	HEADER_ACCEPT_LANGUAGE,
	HEADER_CONTENT_TYPE,
} from '../constants/PortletCommonConstants';
import LiferayUtil from './LiferayUtil';

const TIMEOUT = 60000;

export default class ServiceUtil {
	static getTimeout() {
		return TIMEOUT;
	}

	static createCancelTokenSource(): CancelTokenSource {
		const cancelToken = Axios.CancelToken;

		return cancelToken.source();
	}

	static cancel(cancelTokenSource: CancelTokenSource | undefined): void {
		if (cancelTokenSource) {
			cancelTokenSource.cancel('Cancelled http request');
		}
	}

	static isCancel(ex: any): boolean {
		return Axios.isCancel(ex);
	}

	static isTimeout(ex: any): boolean {
		return ex && ex.code === 'ECONNABORTED';
	}

	static getDefaultHeaders(): any {
		return {
			[HEADER_ACCEPT_LANGUAGE]: LiferayUtil.getLocaleId(),
			[HEADER_CONTENT_TYPE]: 'application/json',
		};
	}

	static getRequestConfig(
		headers: any = {},
		cancelTokenSource?: CancelTokenSource
	): AxiosRequestConfig {
		return {
			cancelToken: cancelTokenSource?.token,
			headers: {...ServiceUtil.getDefaultHeaders(), ...headers},
			timeout: ServiceUtil.getTimeout(),
		};
	}
}
