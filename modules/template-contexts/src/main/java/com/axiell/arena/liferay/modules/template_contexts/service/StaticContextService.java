package com.axiell.arena.liferay.modules.template_contexts.service;

import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.theme.ThemeDisplay;

import java.io.IOException;

public interface StaticContextService {

    ThemeDisplay getThemeDisplay();

    ServiceContext getServiceContext();

    String getAssetViewURL(LiferayPortletRequest liferayPortletRequest,
                           LiferayPortletResponse liferayPortletResponse, AssetEntry assetEntry);

    String URLtoString(String location) throws IOException;
}
