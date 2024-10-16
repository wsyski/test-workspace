package com.axiell.arena.liferay.modules.arena.configuration;

import aQute.bnd.annotation.metatype.Meta;
import com.axiell.arena.liferay.modules.arena.constants.ArenaConstants;
import com.axiell.arena.liferay.modules.arena.constants.ArenaPortletKeys;
import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;

@ExtendedObjectClassDefinition(
        category = ArenaPortletKeys.CONFIGURATION_CATEGORY, scope = ExtendedObjectClassDefinition.Scope.SYSTEM
)
@Meta.OCD(
        id = ArenaConstants.ARENA_SYSTEM_CONFIGURATION_ID,
        localization = ArenaPortletKeys.RESOURCE_BUNDLE, name = "arena-system-configuration"
)
public interface ArenaSystemConfiguration {
    @Meta.AD(deflt = "http://arena-central:16517/central-rest/api/v1", required = false)
    String centralApiEndpoint();

    @Meta.AD(deflt = "http://arena-local:16520/local-rest/api/v1", required = false)
    String localApiEndpoint();

    @Meta.AD(deflt = "http://arena-federated-search:9799", required = false)
    String federatedSearchApiEndpoint();

    @Meta.AD(deflt = "http://arena-transaction:9610", required = false)
    String transactionApiEndpoint();
}
