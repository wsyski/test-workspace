import {LIFERAY_PARAMS_DEFAULT, LiferayUtil} from '@arena/lib-portlet-common';
import {TestUtil} from '@arena/lib-portlet-test';
import {mount} from 'enzyme';
import toJson from 'enzyme-to-json';
import React from 'react';

import AppContainer from '../AppContainer';
import {PORTLET_INSTANCE_DEFAULT} from '../constants/LiferayParamsConstants';

jest.mock('react-i18next');

describe('AppContainer Component', () => {
	let wrapper;
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
		jest.spyOn(LiferayUtil, 'getLayoutRelativeURL').mockImplementation(
			() => '/web/arena/welcome'
		);
		const liferayParamsWithDefaults = LiferayUtil.setLiferayParamsDefaults(
			LIFERAY_PARAMS_DEFAULT,
			PORTLET_INSTANCE_DEFAULT
		);
		wrapper = mount(
			<AppContainer liferayParams={liferayParamsWithDefaults} />
		);

		// console.log(wrapper.debug());
	});

	afterEach(() => {
		jest.clearAllMocks();
		wrapper.unmount();
	});

	test('AppContainer should mount without errors', () => {
		const component = TestUtil.findDeepByTestAttr(wrapper, 'appContainer');
		expect(component.length).toBe(1);
	});

	test('matches snapshot', () => {
		expect(toJson(wrapper)).toMatchSnapshot();
	});
});
