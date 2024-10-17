package com.axiell.arena.liferay.modules.template_contexts.service;

import com.axiell.arena.liferay.modules.common_services.configuration.CommonServicesGroupConfiguration;
import com.axiell.arena.liferay.modules.common_services.configuration.CommonServicesSystemConfiguration;

public interface CommonServicesConfig {

    CommonServicesSystemConfiguration getSystemConfiguration();

    CommonServicesGroupConfiguration getGroupConfiguration(long groupId);
}
