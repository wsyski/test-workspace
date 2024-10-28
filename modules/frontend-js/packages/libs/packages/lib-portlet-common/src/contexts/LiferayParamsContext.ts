import * as React from 'react';

import {LiferayParams} from '../index';

const PORTLET_NAMESPACE_DEFAULT = '_portlet_namespace_';

export const LIFERAY_PARAMS_DEFAULT: LiferayParams = {
	configuration: {
		portletInstance: {},
		system: {},
	},
	contextPath: '',
	portletElementId: 'js-portlet-' + PORTLET_NAMESPACE_DEFAULT,
	portletNamespace: PORTLET_NAMESPACE_DEFAULT,
};

const LiferayParamsContext = React.createContext(LIFERAY_PARAMS_DEFAULT);

export default LiferayParamsContext;
