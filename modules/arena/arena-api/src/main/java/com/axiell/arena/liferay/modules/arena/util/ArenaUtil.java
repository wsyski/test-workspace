package com.axiell.arena.liferay.modules.arena.util;

import com.axiell.arena.liferay.modules.arena.configuration.ArenaGroupConfiguration;
import com.axiell.arena.liferay.modules.arena.configuration.ArenaSystemConfiguration;
import com.axiell.arena.liferay.modules.arena.constants.ArenaConstants;
import com.axiell.arena.liferay.modules.arena.exception.ArenaRuntimeException;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.module.configuration.ConfigurationException;
import com.liferay.portal.configuration.module.configuration.ConfigurationProviderUtil;
import com.liferay.portal.kernel.settings.GroupServiceSettingsLocator;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.WebKeys;
import lombok.CustomLog;
import org.apache.commons.lang3.StringUtils;

import javax.portlet.PortletRequest;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.ZoneId;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@CustomLog
public class ArenaUtil {
    private static final Pattern FRIENDLY_URL_PATTERN = Pattern.compile("^(?:/(?:[a-z]{2}(?:_[A-Z]{2})?))?(?:/widget)?(/group|/user|/web)(/[^/^\\s^\\?^;]+)(?:[^\\s^\\?]*)$");

    public static ThemeDisplay getThemeDisplay(final PortletRequest portletRequest) {
        return (ThemeDisplay) portletRequest.getAttribute(WebKeys.THEME_DISPLAY);
    }

    public static String getGroupDisplayUrl(final PortletRequest portletRequest) {
        ThemeDisplay themeDisplay = getThemeDisplay(portletRequest);
        return themeDisplay.getSiteGroup().getDisplayURL(themeDisplay);
    }

    public static Locale getLocale(final PortletRequest portletRequest) {
        ThemeDisplay themeDisplay = getThemeDisplay(portletRequest);
        return themeDisplay.getLocale();
    }

    public static ZoneId getZoneId(User user) {
        if (user == null || user.getTimeZoneId() == null) {
            return ZoneId.systemDefault();
        } else {
            return ZoneId.of(user.getTimeZoneId());
        }
    }

    public static String parseFriendlyUrl(final String url) {
        URI uri;
        try {
            uri = new URI(url);
        } catch (URISyntaxException ex) {
            log.error(ex.getMessage(), ex);
            return null;
        }
        String path = uri.getPath();
        if (StringUtils.isBlank(path)) {
            return null;
        }
        Matcher matcher = FRIENDLY_URL_PATTERN.matcher(path);
        if (matcher.matches()) {
            return matcher.group(2);
        } else {
            return null;
        }
    }

    public static String getFriendlyUrl(final PortletRequest portletRequest) {
        return parseFriendlyUrl(getGroupDisplayUrl(portletRequest));
    }

    public static String getVhost(final PortletRequest portletRequest) {
        return portletRequest.getServerName();
    }

    public static ArenaGroupConfiguration getArenaGroupConfiguration(final long groupId) {
        try {
            return ConfigurationProviderUtil.getConfiguration(ArenaGroupConfiguration.class, new GroupServiceSettingsLocator(groupId, ArenaConstants.ARENA_GROUP_CONFIGURATION_ID));
        } catch (ConfigurationException ex) {
            throw new ArenaRuntimeException(ex.getMessage(), ex);
        }
    }

    public static ArenaSystemConfiguration getArenaSystemConfiguration() {
        try {
            return ConfigurationProviderUtil.getSystemConfiguration(ArenaSystemConfiguration.class);
        } catch (ConfigurationException ex) {
            throw new ArenaRuntimeException(ex.getMessage(), ex);
        }
    }
}
