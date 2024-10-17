package com.axiell.arena.liferay.modules.template_contexts.service;

import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.asset.publisher.util.AssetPublisherHelper;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.HttpUtil;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import java.io.IOException;

@Component(service = StaticContextService.class)
public class StaticContextServiceImpl implements StaticContextService {
    @Reference
    protected AssetPublisherHelper assetPublisherHelper;

    @Override
    public ThemeDisplay getThemeDisplay() {
        return ServiceContextThreadLocal.getServiceContext().getThemeDisplay();
    }

    @Override
    public ServiceContext getServiceContext() {
        return ServiceContextThreadLocal.getServiceContext();
    }

    @Override
    public String getAssetViewURL(LiferayPortletRequest liferayPortletRequest,
                                  LiferayPortletResponse liferayPortletResponse, AssetEntry assetEntry) {

        return assetPublisherHelper.getAssetViewURL(liferayPortletRequest, liferayPortletResponse, assetEntry);
    }

    @Override
    public String URLtoString(String location) throws IOException {
        return HttpUtil.URLtoString(location);
    }
}
