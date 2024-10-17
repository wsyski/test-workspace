package com.axiell.arena.liferay.modules.common_services.constants;

public class CommonServicesConstants {
    public static final String COMMON_SERVICES_API_BUNDLE_NAME = "com.axiell.arena.liferay.modules.common_services.api";
    public static final String COMMON_SERVICES_SYSTEM_CONFIGURATION_ID = "com.axiell.arena.liferay.modules.common_services.configuration.CommonServicesSystemConfiguration";
    public static final String COMMON_SERVICES_GROUP_CONFIGURATION_ID = "com.axiell.arena.liferay.modules.common_services.configuration.CommonServicesGroupConfiguration";

    public static final String KEY_CALENDAR_CUSTOMER_ID = "calendarCustomerId";
    public static final String KEY_CALENDAR_LOCATION_VOCABULARY_ID = "calendarLocationVocabularyId";
    public static final String KEY_CALENDAR_TARGET_AUDIENCE_VOCABULARY_ID = "calendarTargetAudienceVocabularyId";
    public static final String KEY_CALENDAR_DEFAULT_ALLOWED_ATTENDEES = "calendarDefaultAllowedAttendees";
    public static final String KEY_CALENDAR_API_KEY = "calendarApiKey";
    public static final String KEY_OPENING_HOURS_CUSTOMER_ID = "openingHoursCustomerId";
    public static final int CACHE_EXPIRATION_TIME = 60 * 60;

    public static final String HEADER_API_KEY = "X-Axiell-Api-Key";
    public static final String HEADER_CACHE_CONTROL = "Cache-Control";
    public static final String HEADER_CACHE_CONTROL_VALUE = "public, max-age=" + CACHE_EXPIRATION_TIME;
}
