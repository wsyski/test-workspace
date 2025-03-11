package com.axiell.arena.liferay.modules.template_contexts.service;

import com.axiell.arena.liferay.modules.arena.configuration.ArenaGroupConfiguration;
import com.axiell.arena.liferay.modules.arena.configuration.ArenaSystemConfiguration;
import com.axiell.arena.liferay.modules.common_services.configuration.CommonServicesGroupConfiguration;
import com.axiell.arena.liferay.modules.common_services.configuration.CommonServicesSystemConfiguration;
import com.axiell.arena.liferay.modules.common_services.util.CommonServicesUtil;
import com.liferay.portal.configuration.module.configuration.ConfigurationProviderUtil;
import org.osgi.service.component.annotations.Component;

@Component(service = ConfigurationScopeManager.class)
public class ConfigurationScopeManagerImpl implements ConfigurationScopeManager {

    public CommonServicesSystemConfiguration commonServicesSystemConfiguration() {
        return CommonServicesUtil.getCommonServicesSystemConfiguration();
    }

    public CommonServicesGroupConfiguration commonServicesGroupConfiguration(long groupId) {
        return CommonServicesUtil.getCommonServicesGroupConfiguration(groupId);
    }

    public ArenaSystemConfiguration arenaSystemConfiguration() throws Exception {
        return ConfigurationProviderUtil.getSystemConfiguration(ArenaSystemConfiguration.class);
    }

    public ArenaGroupConfiguration arenaGroupConfiguration(long scopeGroupId) throws Exception {
        return ConfigurationProviderUtil.getGroupConfiguration(ArenaGroupConfiguration.class, scopeGroupId);
    }
}
