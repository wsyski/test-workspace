package com.axiell.arena.liferay.modules.common_services.util;

import com.axiell.arena.liferay.modules.arena.exception.ArenaRuntimeException;
import com.axiell.arena.liferay.modules.common_services.configuration.CommonServicesGroupConfiguration;
import com.axiell.arena.liferay.modules.common_services.configuration.CommonServicesSystemConfiguration;
import com.axiell.arena.liferay.modules.common_services.constants.CommonServicesConstants;
import com.liferay.portal.kernel.module.configuration.ConfigurationException;
import com.liferay.portal.configuration.module.configuration.ConfigurationProviderUtil;
import com.liferay.portal.kernel.settings.GroupServiceSettingsLocator;

public class CommonServicesUtil {

    public static CommonServicesGroupConfiguration getCommonServicesGroupConfiguration(final long groupId) {
        try {
            return ConfigurationProviderUtil.getConfiguration(CommonServicesGroupConfiguration.class, new GroupServiceSettingsLocator(groupId, CommonServicesConstants.COMMON_SERVICES_GROUP_CONFIGURATION_ID));
        } catch (ConfigurationException ex) {
            throw new ArenaRuntimeException(ex.getMessage(), ex);
        }
    }

    public static CommonServicesSystemConfiguration getCommonServicesSystemConfiguration() {
        try {
            return ConfigurationProviderUtil.getSystemConfiguration(CommonServicesSystemConfiguration.class);
        } catch (ConfigurationException ex) {
            throw new ArenaRuntimeException(ex.getMessage(), ex);
        }
    }
}
