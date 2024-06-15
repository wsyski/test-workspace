/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React from 'react';
import TestRenderer from 'react-test-renderer';

import ArenaIcon, {ArenaIconSpriteContext} from '../ArenaIcon';

describe('ArenaIcon', () => {
	it('renders', () => {
		const testRenderer = TestRenderer.create(
			<ArenaIcon
				spritemap="/path/to/some/resource.svg"
				symbol="cool-icon"
			/>
		);

		expect(testRenderer.toJSON()).toMatchSnapshot();
	});

	it('renders with context spritemap', () => {
		const testRenderer = TestRenderer.create(
			<ArenaIconSpriteContext.Provider value="foo/bar.svg">
				<ArenaIcon symbol="cool-icon" />
			</ArenaIconSpriteContext.Provider>
		);

		expect(testRenderer.toJSON()).toMatchSnapshot();
	});
});
