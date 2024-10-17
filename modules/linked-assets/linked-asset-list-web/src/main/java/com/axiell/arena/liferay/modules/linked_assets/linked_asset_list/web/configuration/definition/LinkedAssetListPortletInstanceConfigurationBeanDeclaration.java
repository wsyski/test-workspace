package com.axiell.arena.liferay.modules.linked_assets.linked_asset_list.web.configuration.definition;

import com.axiell.arena.liferay.modules.linked_assets.linked_asset_list.web.configuration.LinkedAssetListPortletInstanceConfiguration;
import com.liferay.portal.kernel.settings.definition.ConfigurationBeanDeclaration;

public class LinkedAssetListPortletInstanceConfigurationBeanDeclaration implements ConfigurationBeanDeclaration {

    @Override
    public Class<?> getConfigurationBeanClass() {
        return LinkedAssetListPortletInstanceConfiguration.class;
    }
}
