/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {getBoolean} from '../ExtendedLiferayParamsImpl';

describe('ExtendedLiferayParamsImpl', () => {
	test('getBoolean true', () => {
		const value = getBoolean('true');
		expect(value).toEqual(true);
	});

	test('getBoolean false', () => {
		const value = getBoolean('false');
		expect(value).toEqual(false);
	});
});
