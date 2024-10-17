package com.axiell.arena.liferay.modules.template_contexts.model;

import com.axiell.arena.liferay.modules.template_contexts.api.ModelFactory;
import com.axiell.arena.liferay.modules.template_contexts.service.EditIconService;
import com.axiell.arena.liferay.modules.template_contexts.service.StaticContextService;
import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.journal.web.internal.asset.model.JournalArticleAssetRenderer;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.templateparser.TemplateNode;
import com.liferay.portal.kernel.xml.Node;

import java.util.List;
import java.util.Locale;
import java.util.Map;

public abstract class AbstractModelFactory implements ModelFactory {
    private Locale locale;
    private LiferayPortletRequest renderRequest;
    private LiferayPortletResponse renderResponse;
    private List<TemplateNode> templateNodes;

    @SuppressWarnings("unchecked")
    protected String getDataValue(Object map) {
        if (!(map instanceof Map)) {
            throw new IllegalArgumentException("Invalid map!");
        }
        return (String) ((Map<Object, Object>) map).get("data");
    }

    protected String getElementValue(Node node, String name) {
        return node.valueOf(String.format("//dynamic-element[@name='%s']/dynamic-content/text()", name));
    }

    protected String getTemplateNodeString(TemplateNode node, String key) {
        return getDataModelString(node, key);
    }

    protected int getTemplateNodeInteger(TemplateNode node, String key) {
        if (node.containsKey(key)) {
            return Integer.parseInt(getDataModelString(node, key));
        } else {
            return 0;
        }
    }

    protected boolean getTemplateNodeBoolean(TemplateNode node, String key) {
        if (node.containsKey(key)) {
            return Boolean.parseBoolean(getDataModelString(node, key));
        } else {
            return false;
        }
    }

    protected String getDataModelString(Map<String, Object> dataModel, String key) {
        if (dataModel.containsKey(key)) {
            return getDataValue(dataModel.get(key));
        } else {
            return "";
        }
    }

    protected void injectEditIcon(EditIconService editIconService, AssetEntry assetEntry,
                                  AbstractEditableModel abstractEditableModel) throws Exception {
        EditIconService.EditIconModel editIconModel = editIconService.getEditIconModel(assetEntry, locale,
                renderRequest, renderResponse);
        if (editIconModel != null) {
            abstractEditableModel.setEditIconTitle(editIconModel.getEditIconTitle());
            abstractEditableModel.setEditUrl(editIconModel.getEditUrl());
        }
    }

    protected void injectViewUrl(StaticContextService staticContextService, AssetEntry assetEntry,
                                 AbstractViewableModel abstractViewableModel) throws Exception {
        JournalArticleAssetRenderer renderer = (JournalArticleAssetRenderer) assetEntry.getAssetRenderer();
        String assetViewURL = staticContextService.getAssetViewURL(renderRequest, renderResponse, assetEntry);
        abstractViewableModel.setViewUrl(renderer.getURLViewInContext(renderRequest, renderResponse, assetViewURL));
    }

    public Locale getLocale() {
        return locale;
    }

    public void setLocale(Locale locale) {
        this.locale = locale;
    }

    public LiferayPortletRequest getRenderRequest() {
        return renderRequest;
    }

    public void setRenderRequest(LiferayPortletRequest renderRequest) {
        this.renderRequest = renderRequest;
    }

    public void setRenderResponse(LiferayPortletResponse renderResponse) {
        this.renderResponse = renderResponse;
    }

    public List<TemplateNode> getTemplateNodes() {
        return templateNodes;
    }

    public void setTemplateNodes(List<TemplateNode> templateNodes) {
        this.templateNodes = templateNodes;
    }
}
