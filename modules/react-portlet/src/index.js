import React from 'react';
import ReactDOM from 'react-dom';
import AppComponent from "./AppComponent";
import 'react-shared';

var PORTLET_NAMESPACE_DEFAULT = '_portlet_namespace_';

var LIFERAY_PARAMS_DEFAULT = {
	configuration: {
		portletInstance: {},
		system: {},
	},
	contextPath: '',
	portletElementId: 'js-portlet-' + PORTLET_NAMESPACE_DEFAULT,
	portletNamespace: PORTLET_NAMESPACE_DEFAULT,
};

var DRINK = 'drink';

var PORTLET_INSTANCE_DEFAULT = {
	[DRINK]: 'orange',
};

function setLiferayParamsDefaults(
	liferayParams,
	portletInstance,
	system
) {
	let liferayParamsConfiguration = liferayParams.configuration;
	if (portletInstance) {
		liferayParamsConfiguration = {
			...liferayParamsConfiguration,
			portletInstance: {...portletInstance, ...liferayParamsConfiguration.portletInstance},
		};
	}
	if (system) {
		liferayParamsConfiguration = {
			...liferayParamsConfiguration,
			system: {...system, ...liferayParamsConfiguration.system},
		};
	}

	return {...liferayParams, configuration: liferayParamsConfiguration};
}

function isPortal() {
	return typeof Liferay.once !== 'undefined';
}

export default function main(liferayParams) {
	var liferayParamsWithDefaults = setLiferayParamsDefaults(
		liferayParams,
		PORTLET_INSTANCE_DEFAULT,
		undefined
	);
	var portletElement = document.getElementById(liferayParamsWithDefaults.portletElementId);
	var markup = React.createElement(AppComponent, {...liferayParamsWithDefaults});
	ReactDOM.render(isPortal() ? (
			<React.Fragment>{markup}</React.Fragment>
		) : (
			<React.StrictMode>{markup}</React.StrictMode>
		),
		portletElement
	);
	if (isPortal()) {
		Liferay.once('destroyPortlet', () => {
			ReactDOM.unmountComponentAtNode(portletElement);
		});
	}
};

if (!isPortal()) {
	main(LIFERAY_PARAMS_DEFAULT);
}
