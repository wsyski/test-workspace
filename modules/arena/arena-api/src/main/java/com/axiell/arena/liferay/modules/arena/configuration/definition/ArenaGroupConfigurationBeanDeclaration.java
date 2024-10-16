package com.axiell.arena.liferay.modules.arena.configuration.definition;

import com.axiell.arena.liferay.modules.arena.configuration.ArenaGroupConfiguration;
import com.liferay.portal.kernel.settings.definition.ConfigurationBeanDeclaration;
import org.osgi.service.component.annotations.Component;

@Component
public class ArenaGroupConfigurationBeanDeclaration
	implements ConfigurationBeanDeclaration {

	@Override
	public Class<?> getConfigurationBeanClass() {
		return ArenaGroupConfiguration.class;
	}

}
