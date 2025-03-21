import {LIFERAY_PARAMS_DEFAULT, LiferayUtil} from '@arena/lib-portlet-common';
import {screen} from '@testing-library/dom';
import {render} from '@testing-library/react';
import React from 'react';

import '@testing-library/jest-dom'

import AppContainer from '../AppContainer';
import {PORTLET_INSTANCE_DEFAULT} from '../constants/LiferayParamsConstants';

jest.mock('react-i18next');

describe('AppContainer Component', () => {
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


    });

    afterEach(() => {
        jest.clearAllMocks();
    });

    test('AppContainer should mount without errors', () => {
        const liferayParamsWithDefaults = LiferayUtil.setLiferayParamsDefaults(
            LIFERAY_PARAMS_DEFAULT,
            PORTLET_INSTANCE_DEFAULT
        );
        const {container} = render(
            <AppContainer liferayParams={liferayParamsWithDefaults}/>
        );

        screen.debug();
        const elements = screen.getAllByTestId('appContainer');
        expect(elements.length).toBe(1);
        expect(container).toMatchSnapshot();
    });

});
