package com.axiell.arena.liferay.modules.arena.configuration;

import aQute.bnd.annotation.metatype.Meta;
import com.axiell.arena.liferay.modules.arena.constants.ArenaConstants;
import com.axiell.arena.liferay.modules.arena.constants.ArenaPortletKeys;
import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;

@ExtendedObjectClassDefinition(
        category = ArenaPortletKeys.CONFIGURATION_CATEGORY, scope = ExtendedObjectClassDefinition.Scope.GROUP
)
@Meta.OCD(
        id = ArenaConstants.ARENA_GROUP_CONFIGURATION_ID,
        localization = ArenaPortletKeys.RESOURCE_BUNDLE, name = "arena-group-configuration"
)
public interface ArenaGroupConfiguration {
    @Meta.AD(deflt = "", required = false)
    String federatedSearchCustomerAlias();

    @Meta.AD(deflt = "", required = false)
    String transactionTenantId();

    @Meta.AD(deflt = "", required = false)
    String federatedSearchSourceConfig();

}
