import {I18nUtil, LIFERAY_PARAMS_DEFAULT, LiferayUtil} from '@arena/lib-portlet-common';
import React, { StrictMode } from "react";
import ReactDOM from 'react-dom';
import {I18nextProvider} from 'react-i18next';

import AppContainer from './AppContainer';
import {PORTLET_INSTANCE_DEFAULT} from './constants/LiferayParamsConstants';

const isDevelopment = () => {
	return false;
}

const index = (liferayParams) => {
	const liferayParamsWithDefaults = LiferayUtil.setLiferayParamsDefaults(
		liferayParams,
		PORTLET_INSTANCE_DEFAULT
	);
	const i18nInstance = I18nUtil.init(liferayParamsWithDefaults.contextPath);

	const markup = React.createElement(
		I18nextProvider,
		{i18n: i18nInstance},
		React.createElement(AppContainer, {
			liferayParams: liferayParamsWithDefaults,
		})
	);
	ReactDOM.render(
		isDevelopment() ? React.createElement(StrictMode, null, markup) : markup,
		document.getElementById(liferayParamsWithDefaults.portletElementId)
	);
};

if (isDevelopment()) {
	index(LIFERAY_PARAMS_DEFAULT);
}

export default index;
