/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {AxiosResponse} from "axios";
const {default: Axios} = require('axios');

import {COMMON_SERVICES_CONFIG_DEFAULT} from '../contexts/CommonServicesConfigContext';
import {CommonServicesConfig} from '../index';
import LiferayUtil from '../utils/LiferayUtil';
import MiscUtil from '../utils/MiscUtil';
import ServiceUtil from '../utils/ServiceUtil';

export default class CommonServicesConfigService {
    static async getConfig(): Promise<CommonServicesConfig> {
        const groupId = LiferayUtil.getScopeGroupId();
        const url = `${LiferayUtil.getPortalURL()}/o/common-services/v1.0/groups/${groupId}/config`;
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
}
