package com.axiell.arena.liferay.modules.linked_assets.linked_asset_list.web.portlet;

import com.axiell.arena.liferay.modules.arena.constants.ArenaPortletKeys;
import com.axiell.arena.liferay.modules.linked_assets.linked_asset_list.web.configuration.LinkedAssetListPortletInstanceConfiguration;
import com.axiell.arena.liferay.modules.linked_assets.linked_asset_list.web.constants.LinkedAssetListPortletKeys;
import com.liferay.asset.kernel.model.AssetCategory;
import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.asset.kernel.service.AssetEntryLocalServiceUtil;
import com.liferay.asset.kernel.service.AssetVocabularyServiceUtil;
import com.liferay.asset.kernel.service.persistence.AssetEntryQuery;
import com.liferay.journal.model.JournalArticle;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.module.configuration.ConfigurationException;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.*;
import org.osgi.service.component.annotations.Component;

import javax.portlet.*;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toSet;

@Component(
        configurationPid = LinkedAssetListPortletKeys.LINKED_ASSET_LIST_PORTLET_CONFIGURATION_ID,
        immediate = true,
        property = {
                "com.liferay.portlet.add-default-resource=true",
                "com.liferay.portlet.ajaxable=true",
                "com.liferay.portlet.display-category=" + ArenaPortletKeys.DISPLAY_CATEGORY,
                "com.liferay.portlet.instanceable=true",
                "com.liferay.portlet.header-portlet-css=/css/main.css",
                "com.liferay.portlet.css-class-wrapper=" + LinkedAssetListPortletKeys.CSS_CLASS_WRAPPER,
                "javax.portlet.display-name=" + LinkedAssetListPortletKeys.LINKED_ASSET_LIST_PORTLET_DISPLAY_NAME,
                "javax.portlet.init-param.config-template=" + ArenaPortletKeys.CONFIGURATION_TEMPLATE,
                "javax.portlet.init-param.view-template=" + ArenaPortletKeys.VIEW_TEMPLATE,
                "javax.portlet.init-param.template-path=/",
                "javax.portlet.name=" + LinkedAssetListPortletKeys.LINKED_ASSET_LIST_PORTLET_NAME,
                "javax.portlet.resource-bundle=" + ArenaPortletKeys.RESOURCE_BUNDLE,
                "javax.portlet.preferences=" + ArenaPortletKeys.DEFAULT_PORTLET_PREFERENCES,
                "javax.portlet.security-role-ref=power-user,user",
                "javax.portlet.version=3.0"
        },
        service = Portlet.class
)
public class LinkedAssetListPortlet extends MVCPortlet {
    private static final String ATTR_LAYOUT_ASSET_ENTRY = "layoutAssetEntry";
    private static final Log _log = LogFactoryUtil.getLog(LinkedAssetListPortlet.class);

    private boolean contains(final Set<Long> filterIds, final long[] ids) {
        return Arrays.stream(ids).anyMatch(filterIds::contains);
    }

    //Filter duplicate assetEntry
    private List<AssetEntry> getFilteredAssetEntries(List<AssetEntry> allAssetEntries, Set<Long> filterCategoryIds) {
        List<AssetEntry> assetEntries = new ArrayList<>();
        if (allAssetEntries != null) {
            for (AssetEntry entry : allAssetEntries) {
                if (filterCategoryIds == null || filterCategoryIds.isEmpty() || contains(filterCategoryIds, entry.getCategoryIds())) {
                    assetEntries.add(entry);
                }
            }
        }
        return assetEntries;
    }

    private LinkedAssetListPortletInstanceConfiguration getPortletInstanceConfiguration(RenderRequest renderRequest) throws PortletException {
        ThemeDisplay themeDisplay = (ThemeDisplay) renderRequest.getAttribute(WebKeys.THEME_DISPLAY);
        try {
            return themeDisplay.getPortletDisplay().getPortletInstanceConfiguration(LinkedAssetListPortletInstanceConfiguration.class);
        } catch (ConfigurationException ex) {
            _log.error(ex.getMessage(), ex);
            throw new PortletException(ex);
        }
    }

    @Override
    public void render(RenderRequest renderRequest, RenderResponse renderResponse) throws IOException, PortletException {
        PortletSession portletSession = renderRequest.getPortletSession();

        AssetEntry layoutAssetEntry = (AssetEntry) renderRequest.getAttribute(WebKeys.LAYOUT_ASSET_ENTRY);
        if (layoutAssetEntry == null) {
            layoutAssetEntry = (AssetEntry) portletSession.getAttribute(ATTR_LAYOUT_ASSET_ENTRY);
        } else {
            portletSession.setAttribute(ATTR_LAYOUT_ASSET_ENTRY, layoutAssetEntry);
        }

        if (layoutAssetEntry != null) {
            LinkedAssetListPortletInstanceConfiguration portletInstanceConfiguration = getPortletInstanceConfiguration(renderRequest);
            List<AssetEntry> assetEntries = getSelectedAssetEntries(layoutAssetEntry);
            Set<Long> filterCategoryIds = Arrays.stream(portletInstanceConfiguration.categoryIds()).mapToLong(Long::parseLong).boxed().collect(toSet());
            assetEntries = getFilteredAssetEntries(assetEntries, filterCategoryIds);
            if (!assetEntries.isEmpty()) {
                setAssetEntryURL(assetEntries, renderRequest, renderResponse);
            }
            renderRequest.setAttribute(LinkedAssetListPortletKeys.ATTR_ASSET_ENTRIES, assetEntries);
        }
        super.render(renderRequest, renderResponse);
    }

    public void setAssetEntryURL(List<AssetEntry> assetList, RenderRequest request, RenderResponse response) {
        assetList.forEach(assetEntry -> {
            try {
                String link = assetEntry.getAssetRenderer()
                        .getURLViewInContext(
                                (LiferayPortletRequest) request,
                                (LiferayPortletResponse) response,
                                ""
                        );

                assetEntry.setUrl(link);
            } catch (Exception ex) {
                _log.error(ex.getMessage(), ex);
            }
        });
    }

    private List<AssetEntry> getSelectedAssetEntries(final AssetEntry layoutAssetEntry) {
        if (layoutAssetEntry.getCategories().isEmpty()) {
            return new ArrayList<AssetEntry>();
        } else {
            AssetEntryQuery assetEntryQuery = new AssetEntryQuery();
            assetEntryQuery.setGroupIds(new long[]{layoutAssetEntry.getGroupId()});
            assetEntryQuery.setAnyCategoryIds(layoutAssetEntry.getCategoryIds());
            assetEntryQuery.setClassName(JournalArticle.class.getName());
            assetEntryQuery.setOrderByCol1(Field.PUBLISH_DATE);
            assetEntryQuery.setOrderByType1("DESC");
            List<AssetEntry> assetEntries = AssetEntryLocalServiceUtil.getEntries(assetEntryQuery);
            long layoutAssetEntryId = layoutAssetEntry.getEntryId();
            return assetEntries.stream().filter(assetEntry -> assetEntry.getEntryId() != layoutAssetEntryId).collect(Collectors.toList());
        }
    }
}
