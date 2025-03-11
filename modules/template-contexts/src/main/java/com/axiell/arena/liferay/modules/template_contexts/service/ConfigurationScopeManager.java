package com.axiell.arena.liferay.modules.template_contexts.service;

import com.axiell.arena.liferay.modules.arena.configuration.ArenaGroupConfiguration;
import com.axiell.arena.liferay.modules.arena.configuration.ArenaSystemConfiguration;
import com.axiell.arena.liferay.modules.common_services.configuration.CommonServicesGroupConfiguration;
import com.axiell.arena.liferay.modules.common_services.configuration.CommonServicesSystemConfiguration;

public interface ConfigurationScopeManager {

    CommonServicesSystemConfiguration commonServicesSystemConfiguration();

    CommonServicesGroupConfiguration commonServicesGroupConfiguration(long groupId);

    ArenaSystemConfiguration arenaSystemConfiguration() throws Exception;

    ArenaGroupConfiguration arenaGroupConfiguration(long scopeGroupId) throws Exception;
}
