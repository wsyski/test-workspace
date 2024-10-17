package com.axiell.arena.liferay.modules.template_contexts.model;

import com.axiell.arena.liferay.modules.template_contexts.api.DynamicListFactoryBuilderFactory;
import com.axiell.arena.liferay.modules.template_contexts.api.ModelFactory;
import com.axiell.arena.liferay.modules.template_contexts.api.ModelFactoryBuilder;
import com.axiell.arena.liferay.modules.template_contexts.service.CatalogueReferenceService;
import com.axiell.arena.liferay.modules.template_contexts.service.EditIconService;
import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.web.internal.asset.model.JournalArticleAssetRenderer;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.SAXReaderUtil;
import org.apache.commons.lang3.StringUtils;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import java.util.Map;

@Component(
        immediate = true,
        service = DynamicListFactoryBuilderFactory.class
)
public class DynamicListFactoryBuilderFactoryImpl implements DynamicListFactoryBuilderFactory {

    private CatalogueReferenceService catalogueReferenceService;
    private EditIconService editIconService;

    @Override
    public ModelFactoryBuilder assetFactory() {
        return new AbstractModelFactoryBuilder() {

            @Override
            protected ModelFactory getFactoryInstance() {
                DynamicListFactory dynamicListFactory = new DynamicListFactory();
                dynamicListFactory.setLocale(getLocale());
                dynamicListFactory.setRenderRequest(getRenderRequest());
                dynamicListFactory.setRenderResponse(getRenderResponse());
                dynamicListFactory.setTemplateNodes(getTemplateNodes());
                return dynamicListFactory;
            }
        };
    }

    @Override
    public String getModelName() {
        return "Dynamic List";
    }

    class DynamicListFactory extends AbstractModelFactory {
        @Override
        public DynamicListModel newInstance(Map<String, Object> dataModel) {
            DynamicListModel dynamicListDto = new DynamicListModel();
            dynamicListDto.setLimit(Integer.parseInt(getDataModelString(dataModel, "limit")));
            dynamicListDto.setQuery(getDataModelString(dataModel, "query"));
            dynamicListDto.setSortField(getDataModelString(dataModel, "sortField"));
            dynamicListDto.setSortDirection(getDataModelString(dataModel, "sortDirection"));
            dynamicListDto.setShowExtent(Boolean.parseBoolean(getDataModelString(dataModel, "showExtent")));
            dynamicListDto.setShowMediaclass(Boolean.parseBoolean(getDataModelString(dataModel, "showMediaClass")));
            dynamicListDto.setTitle(getDataModelString(dataModel, "reserved-article-title"));
            dynamicListDto.setCatalogueReferenceService(catalogueReferenceService);
            return dynamicListDto;
        }

        @Override
        public DynamicListModel newInstance(AssetEntry assetEntry) throws Exception {
            JournalArticleAssetRenderer renderer = (JournalArticleAssetRenderer) assetEntry.getAssetRenderer();
            JournalArticle article = renderer.getArticle();
            Document docXml = SAXReaderUtil.read(article.getContentByLocale(getLocale().toString()));
            DynamicListModel dynamicListDto = new DynamicListModel();
            injectEditIcon(editIconService, assetEntry, dynamicListDto);
            dynamicListDto.setTitle(renderer.getTitle(getLocale()));
            dynamicListDto.setQuery(getElementValue(docXml, "query"));
            dynamicListDto.setSortDirection(getElementValue(docXml, "sortDirection"));
            dynamicListDto.setSortField(getElementValue(docXml, "sortField"));
            dynamicListDto.setLimit(Integer.parseInt(getElementValue(docXml, "limit")));
            String showMediaClass = getElementValue(docXml, "showMediaClass");
            if (StringUtils.isNotBlank(showMediaClass)) {
                dynamicListDto.setShowMediaclass(Boolean.parseBoolean(showMediaClass));
            }
            String showExtent = getElementValue(docXml, "showExtent");
            if (StringUtils.isNotBlank(showExtent)) {
                dynamicListDto.setShowExtent(Boolean.parseBoolean(showExtent));
            }
            dynamicListDto.setCatalogueReferenceService(catalogueReferenceService);
            return dynamicListDto;
        }
    }

    @Reference
    protected void setCatalogueReferenceService(CatalogueReferenceService catalogueReferenceService) {
        this.catalogueReferenceService = catalogueReferenceService;
    }

    @Reference
    protected void setEditIconService(EditIconService editIconService) {
        this.editIconService = editIconService;
    }
}
