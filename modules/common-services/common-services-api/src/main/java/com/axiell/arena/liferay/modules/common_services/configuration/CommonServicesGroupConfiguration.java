package com.axiell.arena.liferay.modules.common_services.configuration;

import aQute.bnd.annotation.metatype.Meta;
import com.axiell.arena.liferay.modules.arena.constants.ArenaPortletKeys;
import com.axiell.arena.liferay.modules.common_services.constants.CommonServicesConstants;
import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;

@ExtendedObjectClassDefinition(
        category = ArenaPortletKeys.CONFIGURATION_CATEGORY, scope = ExtendedObjectClassDefinition.Scope.GROUP
)
@Meta.OCD(
        id = CommonServicesConstants.COMMON_SERVICES_GROUP_CONFIGURATION_ID,
        localization = ArenaPortletKeys.RESOURCE_BUNDLE, name = "common-services-group-configuration"
)
public interface CommonServicesGroupConfiguration {
    @Meta.AD(deflt = "", required = false)
    String calendarCustomerId();

    @Meta.AD(deflt = "0", required = false)
    long calendarLocationVocabularyId();

    @Meta.AD(deflt = "0", required = false)
    long calendarTargetAudienceVocabularyId();

    @Meta.AD(deflt = "1", required = false)
    int calendarDefaultAllowedAttendees();

    @Meta.AD(deflt = "", required = false)
    String coversCustomerId();

    @Meta.AD(deflt = "", required = false)
    String openingHoursCustomerId();

    @Meta.AD(deflt = "", required = false)
    String googleAnalyticsMeasurementId();

    @Meta.AD(deflt = "", required = false)
    String googleTagManagerId();
}
