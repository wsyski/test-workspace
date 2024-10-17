import {TestUtil} from '@arena/lib-portlet-test';
import {cleanup} from '@testing-library/react';
import axios from 'axios';

import {PORTLET_INSTANCE_DEFAULT} from '../../constants/LiferayParamsConstants';
import getRecordResponse from '../../test/resources/getRecordResponse.json';
import searchRecordsResponse from '../../test/resources/searchRecordsResponse.json';
import RecordService from '../RecordService';

jest.mock('axios');

describe('recordService', () => {
	afterEach(() => {
		jest.clearAllMocks();
		cleanup();
	});

	test('searchRecords', async () => {
		axios.get.mockResolvedValue(
			TestUtil.getAxiosResponse(searchRecordsResponse)
		);
		const result = await RecordService.searchRecords(
			'google',
			PORTLET_INSTANCE_DEFAULT.baseUrl,
			PORTLET_INSTANCE_DEFAULT.collection,
			PORTLET_INSTANCE_DEFAULT.pageSize,
			0
		);
		expect(result.hitCount).toEqual(17227);
		expect(axios.get).toHaveBeenCalledTimes(1);
	});

	test('getRecord', async () => {
		axios.get.mockResolvedValue(
			TestUtil.getAxiosResponse(getRecordResponse)
		);
		const result = await RecordService.getRecord(
			'DOAJ01959450X',
			PORTLET_INSTANCE_DEFAULT.baseUrl,
			PORTLET_INSTANCE_DEFAULT.collection
		);
		expect(result.publishDate).toEqual(['2013']);
		expect(axios.get).toHaveBeenCalledTimes(1);
	});
});
