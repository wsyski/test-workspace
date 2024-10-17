package com.axiell.arena.liferay.modules.overrides.k10_search_portlet;

import com.axiell.arena.liferay.modules.overrides.k10_search_portlet.constants.K10SearchPortletKeys;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.settings.ParameterMapSettings;
import com.liferay.portal.kernel.util.Validator;

import javax.portlet.PortletPreferences;
import javax.portlet.RenderRequest;
import java.util.Arrays;
import java.util.List;

public class K10SearchPortletDisplayContext {
    private static final String INPUT_NAME_PREFIX = "_com_liferay_portlet_configuration_web_portlet_PortletConfigurationPortlet_";
    private static final String ATTR_CHECKED = "checked";

    private ViewMode viewMode = ViewMode.FULL;
    private final List<String> selectedCollections;
    private String pageSize = String.valueOf(K10SearchPortletKeys.DEFAULT_PAGE_SIZE);
    private String baseUrl = K10SearchPortletKeys.DEFAULT_BASE_URL;
    private String searchPage = K10SearchPortletKeys.DEFAULT_K10_SEARCH_PAGE;
    private String searchQueryParameter = K10SearchPortletKeys.DEFAULT_SEARCH_QUERY_PARAMETER;

    public K10SearchPortletDisplayContext(final RenderRequest renderRequest, final PortletPreferences portletPreferences) {
        String[] collections = new String[0];
        if (Validator.isNotNull(portletPreferences)) {
            this.viewMode = ViewMode.valueOf(portletPreferences.getValue(K10SearchPortletKeys.KEY_VIEW_MODE, ViewMode.FULL.name()));
            this.pageSize = portletPreferences.getValue(K10SearchPortletKeys.KEY_PAGE_SIZE, String.valueOf(K10SearchPortletKeys.DEFAULT_PAGE_SIZE));
            this.searchPage = portletPreferences.getValue(K10SearchPortletKeys.KEY_K10_SEARCH_PAGE, K10SearchPortletKeys.DEFAULT_K10_SEARCH_PAGE);
            this.searchQueryParameter = portletPreferences.getValue(K10SearchPortletKeys.KEY_SEARCH_QUERY_PARAMETER, K10SearchPortletKeys.DEFAULT_SEARCH_QUERY_PARAMETER);
            this.baseUrl = portletPreferences.getValue(K10SearchPortletKeys.KEY_BASE_URL, K10SearchPortletKeys.DEFAULT_BASE_URL);
            collections = portletPreferences.getValues(K10SearchPortletKeys.KEY_COLLECTION, K10SearchPortletKeys.DEFAULT_COLLECTION);
        }
        this.selectedCollections = Arrays.asList(collections == null ? new String[0] : collections);
    }

    public String getInputName(final String name) {
        return INPUT_NAME_PREFIX + ParameterMapSettings.PREFERENCES_PREFIX  + name + K10SearchPortletKeys.PREFERENCES_POSTFIX;
    }

    public String getCollectionChecked(final String collection) {
        return this.selectedCollections.contains(collection) ? ATTR_CHECKED : StringPool.BLANK;
    }

    public ViewMode getViewMode() {
        return viewMode;
    }

    public List<String> getSelectedCollections() {
        return selectedCollections;
    }

    public String getPageSize() {
        return pageSize;
    }

    public String getBaseUrl() {
        return baseUrl;
    }

    public String getSearchPage() {
        return searchPage;
    }

    public String getSearchQueryParameter() {
        return searchQueryParameter;
    }
}
