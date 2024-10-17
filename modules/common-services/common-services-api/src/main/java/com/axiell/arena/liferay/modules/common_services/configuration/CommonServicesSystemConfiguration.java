package com.axiell.arena.liferay.modules.common_services.configuration;

import aQute.bnd.annotation.metatype.Meta;
import com.axiell.arena.liferay.modules.arena.constants.ArenaPortletKeys;
import com.axiell.arena.liferay.modules.common_services.constants.CommonServicesConstants;
import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;

@ExtendedObjectClassDefinition(
        category = ArenaPortletKeys.CONFIGURATION_CATEGORY, scope = ExtendedObjectClassDefinition.Scope.SYSTEM
)
@Meta.OCD(
        id = CommonServicesConstants.COMMON_SERVICES_SYSTEM_CONFIGURATION_ID,
        localization = ArenaPortletKeys.RESOURCE_BUNDLE, name = "common-services-system-configuration"
)
public interface CommonServicesSystemConfiguration {
    @Meta.AD(deflt = "https://test.axiell.io/api/calendar-event/latest/api", required = false)
    String calendarApiEndpoint();

    @Meta.AD(deflt = "", required = false, type = Meta.Type.Password)
    String calendarApiKey();

    @Meta.AD(deflt = "", required = false)
    String calendarApiUser();

    @Meta.AD(deflt = "", required = false, type = Meta.Type.Password)
    String calendarApiPassword();

    @Meta.AD(deflt = "https://test-api.axiell.com/cover/api", required = false)
    String coversApiEndpoint();

    @Meta.AD(deflt = "https://test.axiell.io/api/openinghours/latest", required = false)
    String openingHoursApiEndpoint();
}
