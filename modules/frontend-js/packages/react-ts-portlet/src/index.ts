import {I18nUtil, LIFERAY_PARAMS_DEFAULT, LiferayParams, LiferayUtil} from "@arena/lib-portlet-common";
import React from 'react';
import ReactDOM from 'react-dom';
import { I18nextProvider } from 'react-i18next';

import AppContainer from './AppContainer';
import { PORTLET_INSTANCE_DEFAULT } from './constants/LiferayParamsConstants';

const isPortal = () => {
    return process.env.NODE_ENV !== 'development';
}

const index = (liferayParams: LiferayParams) => {
    const liferayParamsWithDefaults = LiferayUtil.setLiferayParamsDefaults(liferayParams, PORTLET_INSTANCE_DEFAULT);
    const i18nInstance = I18nUtil.init(liferayParamsWithDefaults.contextPath);
    const portletElement = document.getElementById(liferayParamsWithDefaults.portletElementId);
    const markup = React.createElement(I18nextProvider, { i18n: i18nInstance }, React.createElement(AppContainer, {
        liferayParams: liferayParamsWithDefaults,
    }));
    ReactDOM.render(isPortal() ? React.createElement(React.Fragment, null, markup)  : React.createElement(React.StrictMode, null, markup), document.getElementById(liferayParamsWithDefaults.portletElementId));
};

if (!isPortal()) {
    index(LIFERAY_PARAMS_DEFAULT);
}

export default index;
