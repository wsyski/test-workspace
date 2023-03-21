export var PORTLET_NAMESPACE_DEFAULT = '_portlet_namespace_';

export var LIFERAY_PARAMS_DEFAULT = {
	configuration: {
		portletInstance: {},
		system: {},
	},
	contextPath: '',
	portletElementId: 'js-portlet-' + PORTLET_NAMESPACE_DEFAULT,
	portletNamespace: PORTLET_NAMESPACE_DEFAULT,
};

var LiferayUtil = function () {
    function LiferayUtil() {
    }

    LiferayUtil.isPortal = function isPortal() {
        return typeof window.Liferay.once !== 'undefined';
    };

    LiferayUtil.setLiferayParamsDefaults = function setLiferayParamsDefaults(
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

	return LiferayUtil;
}();

export default LiferayUtil;
