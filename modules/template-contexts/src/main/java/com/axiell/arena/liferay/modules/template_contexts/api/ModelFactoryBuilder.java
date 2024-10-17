package com.axiell.arena.liferay.modules.template_contexts.api;

import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.templateparser.TemplateNode;

import java.util.List;
import java.util.Locale;
import java.util.Map;

public interface ModelFactoryBuilder {
    ModelFactoryBuilder locale(Locale locale);

    ModelFactoryBuilder templateNodes(List<TemplateNode> templateNodes);

    ModelFactoryBuilder renderRequest(LiferayPortletRequest renderRequest);

    ModelFactoryBuilder renderResponse(LiferayPortletResponse renderResponse);

    Object build(Map<String, Object> dataModel) throws Exception;

    Object build(AssetEntry assetEntry) throws Exception;
}
