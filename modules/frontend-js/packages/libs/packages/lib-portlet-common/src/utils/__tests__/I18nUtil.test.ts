/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {AxCurrencyAmount} from '../../models/domain/ax/AxCurrencyAmount';
import I18nUtil from '../I18nUtil';
import LiferayUtil from '../LiferayUtil';

describe('I18nUtil', () => {
	test('date2String', () => {
		jest.spyOn(LiferayUtil, 'getLocale').mockImplementation(() => 'en_US');
		const dateAsString = I18nUtil.date2String(
			new Date(2018, 11, 21, 0, 0, 0)
		);
		expect(dateAsString).toEqual('12/21/2018');
	});

	test('string2Date', () => {
		jest.spyOn(LiferayUtil, 'getLocale').mockImplementation(() => 'en_US');
		const date = I18nUtil.string2Date('12/21/2018');
		expect(date).toEqual(new Date(2018, 11, 21, 0, 0, 0));
	});

	test('currencyAmount', () => {
		jest.spyOn(LiferayUtil, 'getLocale').mockImplementation(() => 'en_US');
		const currencyAmount = {
			amount: 10,
			currency: 'USD',
		} as AxCurrencyAmount;
		const currencyAmountAsString =
			I18nUtil.currencyAmount2String(currencyAmount);
		expect(currencyAmountAsString).toEqual('USD 10');
	});

	test('date2IsoString', () => {
		const isoString = I18nUtil.date2IsoString(
			new Date(2018, 11, 21, 10, 0, 0)
		);
		expect(isoString).toEqual('2018-12-21');
	});

	test('isoString2Date', () => {
		const date = I18nUtil.isoString2Date('2018-12-21');
		expect(date).toEqual(new Date(2018, 11, 21, 0, 0, 0));
	});
});
