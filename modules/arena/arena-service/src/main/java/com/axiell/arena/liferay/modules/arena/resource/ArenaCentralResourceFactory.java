package com.axiell.arena.liferay.modules.arena.resource;

import com.axiell.arena.liferay.modules.arena.configuration.ArenaSystemConfiguration;
import com.axiell.arena.liferay.modules.arena.util.ArenaUtil;

public class ArenaCentralResourceFactory extends BaseResourceFactory {
    public static final ArenaCentralResourceFactory INSTANCE = new ArenaCentralResourceFactory();

    public <T> T createResource(final Class<T> clazz) {
        ArenaSystemConfiguration arenaSystemConfiguration = ArenaUtil.getArenaSystemConfiguration();
        String centralApiEndpoint = arenaSystemConfiguration.centralApiEndpoint();
        return super.createResource(centralApiEndpoint, clazz);
    }
}
