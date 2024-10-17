package com.axiell.arena.liferay.modules.linked_assets.linked_asset_list.web.configuration;

import aQute.bnd.annotation.metatype.Meta;
import com.axiell.arena.liferay.modules.arena.constants.ArenaPortletKeys;
import com.axiell.arena.liferay.modules.linked_assets.linked_asset_list.web.constants.LinkedAssetListPortletKeys;
import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;

@ExtendedObjectClassDefinition(
        category = ArenaPortletKeys.CONFIGURATION_CATEGORY,
        scope = ExtendedObjectClassDefinition.Scope.PORTLET_INSTANCE
)
@Meta.OCD(
        id = LinkedAssetListPortletKeys.LINKED_ASSET_LIST_PORTLET_CONFIGURATION_ID,
        localization = ArenaPortletKeys.RESOURCE_BUNDLE
)
public interface LinkedAssetListPortletInstanceConfiguration {

    @Meta.AD(deflt = "ddmTemplate_ASSET-PUBLISHER-RICH-SUMMARY-FTL", required = false, name = "display-style")
    String displayStyle();

    @Meta.AD(deflt = "", required = false, name = "category-ids")
    String[] categoryIds();

    @Meta.AD(deflt = "facebook|twitter|linkedin", required = false, name = "social-bookmarks-types")
    String[] socialBookmarksTypes();

    @Meta.AD(deflt = "inline", required = false, name = "social-bookmarks-display-style")
    String socialBookmarksDisplayStyle();
}
