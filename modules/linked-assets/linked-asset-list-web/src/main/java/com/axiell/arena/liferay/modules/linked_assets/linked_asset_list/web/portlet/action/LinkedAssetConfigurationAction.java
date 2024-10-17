package com.axiell.arena.liferay.modules.linked_assets.linked_asset_list.web.portlet.action;

import com.axiell.arena.liferay.modules.linked_assets.linked_asset_list.web.constants.LinkedAssetListPortletKeys;
import com.liferay.portal.kernel.portlet.ConfigurationAction;
import com.liferay.portal.kernel.portlet.DefaultConfigurationAction;
import org.osgi.service.component.annotations.Component;

import javax.portlet.ActionParameters;
import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletConfig;
import javax.servlet.http.HttpServletRequest;

@Component(
        immediate = true,
        configurationPid = LinkedAssetListPortletKeys.LINKED_ASSET_LIST_PORTLET_CONFIGURATION_ID,
        property = {
                "javax.portlet.name=" + LinkedAssetListPortletKeys.LINKED_ASSET_LIST_PORTLET_NAME
        },
        service = ConfigurationAction.class
)
public class LinkedAssetConfigurationAction extends DefaultConfigurationAction {

    public static final String PARAM_ASSET_CATEGORY_IDS_PREFIX = "assetCategoryIds";

    @Override
    public String getJspPath(HttpServletRequest request) {
        return "/configuration.jsp";
    }

    @Override
    public void processAction(final PortletConfig portletConfig, final ActionRequest actionRequest, final ActionResponse actionResponse) throws Exception {
        ActionParameters actionParameters = actionRequest.getActionParameters();
        String[] categoryIds = actionParameters.getNames().stream().filter(name -> name.startsWith(PARAM_ASSET_CATEGORY_IDS_PREFIX)).map(actionParameters::getValue).toArray(String[]::new);
        setPreference(actionRequest, LinkedAssetListPortletKeys.KEY_CATEGORY_IDS, categoryIds);
        super.processAction(portletConfig, actionRequest, actionResponse);
    }
}
