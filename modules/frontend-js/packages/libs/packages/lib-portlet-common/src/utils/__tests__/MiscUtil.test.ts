/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import MiscUtil from '../MiscUtil';

describe('MiscUtil', () => {
	test('appendAll nonempty', () => {
		const target = new URLSearchParams();
		target.append('key0', 'value0');
		target.append('key0', 'value1');
		const source = new URLSearchParams();
		target.append('key0', 'value2');
		target.append('key1', 'value3');
		MiscUtil.appendAll(target, source);
		expect(target.toString()).toEqual(
			'key0=value0&key0=value1&key0=value2&key1=value3'
		);
	});

	test('appendAll empty source', () => {
		const target = new URLSearchParams();
		target.append('key0', 'value0');
		target.append('key0', 'value1');
		const source = new URLSearchParams();
		MiscUtil.appendAll(target, source);
		expect(target.toString()).toEqual('key0=value0&key0=value1');
	});

	test('appendAll empty target', () => {
		const target = new URLSearchParams();
		const source = new URLSearchParams();
		target.append('key0', 'value2');
		target.append('key1', 'value3');
		MiscUtil.appendAll(target, source);
		expect(target.toString()).toEqual('key0=value2&key1=value3');
	});

	test('isEmpty true', () => {
		const urlSearchParams = new URLSearchParams();
		expect(MiscUtil.isEmpty(urlSearchParams)).toEqual(true);
	});

	test('isEmpty false', () => {
		const urlSearchParams = new URLSearchParams();
		urlSearchParams.append('key0', 'value0');
		expect(MiscUtil.isEmpty(urlSearchParams)).toEqual(false);
	});

	test('isEqual undefined parameters', () => {
		expect(MiscUtil.isEqual(undefined, undefined)).toEqual(true);
	});

	test('recordValues', () => {
		expect(
			MiscUtil.recordValues({
				key0: ['value0_0', 'value0_1'],
				key1: ['value1_0', 'value1_1'],
			})
		).toEqual(['value0_0', 'value0_1', 'value1_0', 'value1_1']);
	});

	test('randomString', () => {
		const id= MiscUtil.randomString();
		expect(id.length).toEqual(10);
	});

	test('randomString len=10', () => {
		const len = 10;
		const id= MiscUtil.randomString(len);
		expect(id.length).toEqual(len);
	});

	test('normalize', () => {
		expect(MiscUtil.normalize("Crème Brulée")).toEqual("Creme Brulee");
		expect(MiscUtil.normalize("Ste-Gême Family Papers")).toEqual("Ste-Geme Family Papers");
	});
});
