import {TestUtil} from '@arena/lib-portlet-test';
import {cleanup} from '@testing-library/react';
import Axios from 'axios';

import {CommonServicesConfig} from '../../index';
import LiferayUtil from '../../utils/LiferayUtil';
import CommonServicesConfigService from '../CommonServicesConfigService';
import commonServicesConfig from '../__mocks__/resources/commonServicesConfig.json';

jest.mock('axios');
const axiosMock = Axios as jest.Mocked<typeof Axios>;

describe('CommonServicesConfigService', () => {
	beforeEach(() => {
		jest.spyOn(LiferayUtil, 'getScopeGroupId').mockImplementation(
			() => 39847
		);
		jest.spyOn(LiferayUtil, 'getPortalURL').mockImplementation(
			() => 'http://localhost:6080'
		);
		jest.spyOn(LiferayUtil, 'getPathThemeImages').mockImplementation(
			() => '/theme/images'
		);
		jest.spyOn(LiferayUtil, 'getLocale').mockImplementation(() => 'en_US');
	});

	afterEach(() => {
		jest.clearAllMocks();
		cleanup();
	});

	test('getConfig', async () => {
		axiosMock.get.mockResolvedValue(
			TestUtil.getAxiosResponse(commonServicesConfig)
		);
		const CommonServicesConfig: CommonServicesConfig =
			await CommonServicesConfigService.getConfig();
		expect(CommonServicesConfig.federatedSearchCustomerId).toBe(
			'5da0951785879d0dd1dd45e9'
		);
		expect(CommonServicesConfig.federatedSearchApiEndpoint).toBe(
			'http://localhost:9599'
		);
		expect(CommonServicesConfig.openingHoursApiEndpoint).toBe(
			'https://test.axiell.io/api/openinghours/latest'
		);
	});
});
