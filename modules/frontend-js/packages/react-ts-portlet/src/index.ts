/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

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
    const markup = React.createElement(I18nextProvider, { i18n: i18nInstance }, React.createElement(AppContainer, {
        liferayParams: liferayParamsWithDefaults,
    }));
    const node= isPortal() ? React.createElement(React.Fragment, undefined, markup)  : React.createElement(React.StrictMode, undefined, markup);
    ReactDOM.render(node, document.getElementById(liferayParamsWithDefaults.portletElementId));
};

if (!isPortal()) {
    index(LIFERAY_PARAMS_DEFAULT);
}

export default index;
