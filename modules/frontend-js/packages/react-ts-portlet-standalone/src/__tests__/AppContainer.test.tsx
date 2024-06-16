import {shallow, ShallowWrapper} from 'enzyme';
import toJson from 'enzyme-to-json';
import React from 'react';

import AppContainer from '../AppContainer';
import {LIFERAY_PARAMS_DEFAULT, PORTLET_INSTANCE_DEFAULT} from '../constants/LiferayParamsConstants';
import LiferayUtil from "../utils/LiferayUtil";

const findShallowByTestAttr = (
    component: ShallowWrapper,
    attr: string
): ShallowWrapper => {
    return component.find(`[data-test-id='${attr}']`);
}

describe('AppContainer Component', () => {
    let wrapper: ShallowWrapper;
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
        const liferayParamsWithDefaults = LiferayUtil.setLiferayParamsDefaults(
            LIFERAY_PARAMS_DEFAULT,
            PORTLET_INSTANCE_DEFAULT
        );
        wrapper = shallow(
            <AppContainer liferayParams={liferayParamsWithDefaults}/>
        );

        // console.log(wrapper.debug());
    });

    afterEach(() => {
        jest.clearAllMocks();
        wrapper.unmount();
    });

    test('AppContainer should mount without errors', () => {
        const component = findShallowByTestAttr(
            wrapper,
            'appContainer'
        );
        expect(component.length).toBe(1);
    });

    test('matches snapshot', () => {
        expect(toJson(wrapper)).toMatchSnapshot();
    });
});
