package com.axiell.arena.liferay.modules.arena.configuration.definition;

import com.axiell.arena.liferay.modules.arena.configuration.ArenaSystemConfiguration;
import com.liferay.portal.kernel.settings.definition.ConfigurationBeanDeclaration;
import org.osgi.service.component.annotations.Component;

@Component
public class ArenaSystemConfigurationBeanDeclaration implements ConfigurationBeanDeclaration {

    @Override
    public Class<?> getConfigurationBeanClass() {
        return ArenaSystemConfiguration.class;
    }

}
