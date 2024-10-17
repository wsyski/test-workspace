package com.axiell.arena.liferay.modules.template_contexts.service;

import com.axiell.arena.liferay.modules.common_services.configuration.CommonServicesGroupConfiguration;
import com.axiell.arena.liferay.modules.common_services.configuration.CommonServicesSystemConfiguration;
import com.axiell.arena.liferay.modules.common_services.util.CommonServicesUtil;
import org.osgi.service.component.annotations.Component;

@Component(service = CommonServicesConfig.class)
public class CommonServicesConfigImpl implements CommonServicesConfig {

    public CommonServicesSystemConfiguration getSystemConfiguration() {
        return CommonServicesUtil.getCommonServicesSystemConfiguration();
    }

    public CommonServicesGroupConfiguration getGroupConfiguration(long groupId) {
        return CommonServicesUtil.getCommonServicesGroupConfiguration(groupId);
    }
}
