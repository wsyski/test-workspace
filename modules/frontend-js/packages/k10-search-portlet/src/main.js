import {I18nUtil, LiferayUtil} from '@arena/lib-portlet-common';
import React from 'react';
import ReactDOM from 'react-dom';
import {I18nextProvider} from 'react-i18next';

import AppContainer from './AppContainer';
import {PORTLET_INSTANCE_DEFAULT} from './constants/LiferayParamsConstants';

export default function main(liferayParams) {
	const liferayParamsWithDefaults = LiferayUtil.setLiferayParamsDefaults(
		liferayParams,
		PORTLET_INSTANCE_DEFAULT
	);
	const i18nInstance = I18nUtil.init(liferayParamsWithDefaults.contextPath);
	const markup = (
		<I18nextProvider i18n={i18nInstance}>
			<AppContainer liferayParams={liferayParamsWithDefaults} />
		</I18nextProvider>
	);
	ReactDOM.render(
		process.env.NODE_ENV === 'development' ? (
			<React.StrictMode>{markup}</React.StrictMode>
		) : (
			<>{markup}</>
		),
		document.getElementById(liferayParamsWithDefaults.portletElementId)
	);
}
