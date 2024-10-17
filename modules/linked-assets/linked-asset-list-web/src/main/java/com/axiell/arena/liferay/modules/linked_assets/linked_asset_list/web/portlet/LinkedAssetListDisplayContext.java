package com.axiell.arena.liferay.modules.linked_assets.linked_asset_list.web.portlet;

import com.axiell.arena.liferay.modules.arena.constants.ArenaPortletKeys;
import com.axiell.arena.liferay.modules.linked_assets.linked_asset_list.web.configuration.LinkedAssetListPortletInstanceConfiguration;
import com.axiell.arena.liferay.modules.linked_assets.linked_asset_list.web.constants.LinkedAssetListPortletKeys;
import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.asset.kernel.model.AssetVocabulary;
import com.liferay.asset.kernel.service.AssetVocabularyServiceUtil;
import com.liferay.portal.configuration.module.configuration.ConfigurationProviderUtil;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.module.configuration.ConfigurationException;
import com.liferay.portal.kernel.theme.PortletDisplay;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.WebKeys;

import javax.portlet.PortletRequest;
import java.util.*;
import java.util.stream.Collectors;

public class LinkedAssetListDisplayContext {
    private static final String KEY_GLOBAL = "global";
    private static final Log _log = LogFactoryUtil.getLog(LinkedAssetListDisplayContext.class);
    private final ThemeDisplay themeDisplay;
    private final PortletRequest _portletRequest;
    private final LinkedAssetListPortletInstanceConfiguration _linkedAssetListPortletInstanceConfiguration;

    public LinkedAssetListDisplayContext(final PortletRequest portletRequest) throws ConfigurationException {
        this._portletRequest = portletRequest;
        themeDisplay = (ThemeDisplay) _portletRequest.getAttribute(WebKeys.THEME_DISPLAY);
        PortletDisplay portletDisplay = themeDisplay.getPortletDisplay();
        _linkedAssetListPortletInstanceConfiguration = ConfigurationProviderUtil.getPortletInstanceConfiguration(LinkedAssetListPortletInstanceConfiguration.class, themeDisplay);
    }

    public Long getDisplayStyleGroupId() {
        return themeDisplay.getSiteGroupId();
    }

    public LinkedAssetListPortletInstanceConfiguration getLinkedAssetPortletInstanceConfiguration() {
        return _linkedAssetListPortletInstanceConfiguration;
    }

    public Map<Long, String> getVocabularyNames() {
        Map<Long, String> vocabularyNames = new HashMap<>();
        List<AssetVocabulary> assetVocabularies = getAssetVocabularies();
        assetVocabularies.forEach(assetVocabulary -> {
            vocabularyNames.put(assetVocabulary.getVocabularyId(), getTitle(assetVocabulary));
        });
        return vocabularyNames;
    }

    public List<AssetEntry> getAssetEntries() {
        List<AssetEntry> entries = (List<AssetEntry>) _portletRequest.getAttribute(LinkedAssetListPortletKeys.ATTR_ASSET_ENTRIES);
        if (entries == null) {
            entries = new ArrayList<>();
        }
        return entries;
    }

    public String getCategoryIdsAsString() {
        String[] categoryIds = _linkedAssetListPortletInstanceConfiguration.categoryIds();
        String categoryIsAsString = Arrays.stream(categoryIds).collect(Collectors.joining(","));
        return categoryIsAsString;
    }

    private List<AssetVocabulary> getAssetVocabularies() {
        long[] groupIds = PortalUtil.getCurrentAndAncestorSiteGroupIds(themeDisplay.getScopeGroupId());
        return AssetVocabularyServiceUtil.getGroupVocabularies(groupIds);
    }

    private String getTitle(AssetVocabulary assetVocabulary) {
        String title = assetVocabulary.getTitle(themeDisplay.getLanguageId());
        if (assetVocabulary.getGroupId() == themeDisplay.getCompanyGroupId()) {
            title += " (" + LanguageUtil.get(ResourceBundle.getBundle(ArenaPortletKeys.RESOURCE_BUNDLE, themeDisplay.getLocale()), KEY_GLOBAL) + ")";
        }
        return title;
    }
}
