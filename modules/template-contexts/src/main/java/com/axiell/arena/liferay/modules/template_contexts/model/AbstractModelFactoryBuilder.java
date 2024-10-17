package com.axiell.arena.liferay.modules.template_contexts.model;

import com.axiell.arena.liferay.modules.template_contexts.api.ModelFactory;
import com.axiell.arena.liferay.modules.template_contexts.api.ModelFactoryBuilder;
import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.templateparser.TemplateNode;

import java.util.List;
import java.util.Locale;
import java.util.Map;

public abstract class AbstractModelFactoryBuilder implements ModelFactoryBuilder {
    private List<TemplateNode> templateNodes;
    private Locale locale;
    private LiferayPortletRequest renderRequest;
    private LiferayPortletResponse renderResponse;

    @Override
    public ModelFactoryBuilder locale(Locale locale) {
        this.locale = locale;
        return this;
    }

    @Override
    public ModelFactoryBuilder templateNodes(List<TemplateNode> templateNodes) {
        this.templateNodes = templateNodes;
        return this;
    }

    @Override
    public ModelFactoryBuilder renderRequest(LiferayPortletRequest renderRequest) {
        this.renderRequest = renderRequest;
        return this;
    }

    @Override
    public ModelFactoryBuilder renderResponse(LiferayPortletResponse renderResponse) {
        this.renderResponse = renderResponse;
        return this;
    }

    @Override
    public Object build(Map<String, Object> dataModel) throws Exception {
        return getFactoryInstance().newInstance(dataModel);
    }

    @Override
    public Object build(AssetEntry assetEntry) throws Exception {
        return getFactoryInstance().newInstance(assetEntry);
    }

    protected abstract ModelFactory getFactoryInstance();

    protected List<TemplateNode> getTemplateNodes() {
        return templateNodes;
    }

    protected Locale getLocale() {
        return locale;
    }

    protected LiferayPortletRequest getRenderRequest() {
        return renderRequest;
    }

    protected LiferayPortletResponse getRenderResponse() {
        return renderResponse;
    }
}
