const PORTLET_NAMESPACE_DEFAULT = '_portlet_namespace_';

export const LIFERAY_PARAMS_DEFAULT = {
	configuration: {
		portletInstance: {},
		system: {},
	},
	contextPath: '',
	portletElementId: 'js-portlet-' + PORTLET_NAMESPACE_DEFAULT,
	portletNamespace: PORTLET_NAMESPACE_DEFAULT,
};

const DRINK = 'drink';

export const PORTLET_INSTANCE_DEFAULT = {
	[DRINK]: 'orange',
};
