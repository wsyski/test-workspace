const LiferayUtil = function () {
    function LiferayUtil() {
    }

    LiferayUtil.isPortal = function isPortal() {
        return typeof window.Liferay?.FeatureFlags !== 'undefined';
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

	LiferayUtil.PORTLET_NAMESPACE_DEFAULT = '_portlet_namespace_';

	LiferayUtil.LIFERAY_PARAMS_DEFAULT = {
		configuration: {
			portletInstance: {},
			system: {},
		},
		contextPath: '',
		portletElementId: 'js-portlet-' + LiferayUtil.PORTLET_NAMESPACE_DEFAULT,
		portletNamespace: LiferayUtil.PORTLET_NAMESPACE_DEFAULT,
	};

	return LiferayUtil;
}();

export default LiferayUtil;
