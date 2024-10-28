import Axios, {AxiosResponse} from 'axios';

import {COMMON_SERVICES_CONFIG_DEFAULT} from '../contexts/CommonServicesConfigContext';
import {CommonServicesConfig} from '../index';
import LiferayUtil from '../utils/LiferayUtil';
import MiscUtil from '../utils/MiscUtil';
import ServiceUtil from '../utils/ServiceUtil';

export default class CommonServicesConfigService {
	static async getConfig(): Promise<CommonServicesConfig> {
		const groupId = LiferayUtil.getScopeGroupId();
		const url = `${LiferayUtil.getPortalURL()}/o/common-services/v5.7/groups/${groupId}/config`;
		const response: AxiosResponse = await Axios.get(
			url,
			ServiceUtil.getRequestConfig({})
		);
		const data = response.data;

		return {
			...COMMON_SERVICES_CONFIG_DEFAULT,
			...data,
			federatedSearchSourceConfig: MiscUtil.isEmpty(
				data.federatedSearchSourceConfig
			)
				? COMMON_SERVICES_CONFIG_DEFAULT.federatedSearchSourceConfig
				: JSON.parse(data.federatedSearchSourceConfig),
		} as CommonServicesConfig;
	}

	static getCoverUrl(identifier: Record<string, string[]> = {}, commonServicesConfig: CommonServicesConfig, size: string = 'SMALL'): string {
		const urlSearchParams = new URLSearchParams();
		urlSearchParams.append("size", size);
		Object.entries(identifier).forEach(([, values]) => {
			values.forEach(value => urlSearchParams.append("coverIds", value));
		});

		return `${commonServicesConfig.coversApiEndpoint}/covers/${commonServicesConfig.coversCustomerId}?${urlSearchParams.toString()}`;
	}
}
