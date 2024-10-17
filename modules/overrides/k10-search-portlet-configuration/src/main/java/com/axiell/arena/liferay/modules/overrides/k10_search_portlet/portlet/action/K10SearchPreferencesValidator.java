package com.axiell.arena.liferay.modules.overrides.k10_search_portlet.portlet.action;

import com.axiell.arena.liferay.modules.overrides.k10_search_portlet.constants.K10SearchPortletKeys;
import com.liferay.petra.string.StringPool;
import org.apache.commons.lang3.StringUtils;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.ConfigurationPolicy;

import javax.portlet.PortletPreferences;
import javax.portlet.PreferencesValidator;
import javax.portlet.ValidatorException;
import java.util.Collections;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component(
        configurationPolicy = ConfigurationPolicy.OPTIONAL,
        immediate = true,
        property = {
                "javax.portlet.name=" + K10SearchPortletKeys.K10_SEARCH_PORTLET_NAME,
                "service.ranking:Integer=100"
        },
        service = PreferencesValidator.class
)
public class K10SearchPreferencesValidator implements PreferencesValidator {
    private static final String ERROR_INVALID_PAGE_SIZE = "Invalid page size";
    private static final String ERROR_INVALID_K10_SEARCH_PAGE = "Invalid k10 search page";
    private final Pattern VALID_URL_PATTERN = Pattern.compile("[0-9a-z_\\-][0-9a-z_\\-/]*[0-9a-z_\\-]*");

    @Override
    public void validate(PortletPreferences portletPreferences) throws ValidatorException {

        String pageSizeAsString = portletPreferences.getValue(K10SearchPortletKeys.KEY_PAGE_SIZE, StringPool.BLANK);
        try {
            if (StringUtils.isBlank(pageSizeAsString) || Integer.parseInt(pageSizeAsString) <= 0) {
                throw new ValidatorException(ERROR_INVALID_PAGE_SIZE, Collections.singletonList(pageSizeAsString));
            }
        } catch (IllegalArgumentException ex) {
            throw new ValidatorException(ERROR_INVALID_PAGE_SIZE, Collections.singletonList(pageSizeAsString));
        }
        String eventDetailPage = portletPreferences.getValue(K10SearchPortletKeys.KEY_K10_SEARCH_PAGE, StringPool.BLANK);
        if (StringUtils.isNotBlank(eventDetailPage)) {
            Matcher matcher = VALID_URL_PATTERN.matcher(eventDetailPage);
            if (!matcher.matches()) {
                throw new ValidatorException(ERROR_INVALID_K10_SEARCH_PAGE, Collections.singletonList(eventDetailPage));
            }
        }
    }
}
