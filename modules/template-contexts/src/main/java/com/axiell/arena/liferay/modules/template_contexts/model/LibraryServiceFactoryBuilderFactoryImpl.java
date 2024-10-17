package com.axiell.arena.liferay.modules.template_contexts.model;

import com.axiell.arena.liferay.modules.template_contexts.api.LibraryServiceFactoryBuilderFactory;
import com.axiell.arena.liferay.modules.template_contexts.api.ModelFactory;
import com.axiell.arena.liferay.modules.template_contexts.api.ModelFactoryBuilder;
import com.axiell.arena.liferay.modules.template_contexts.service.EditIconService;
import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.web.internal.asset.model.JournalArticleAssetRenderer;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.SAXReaderUtil;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import java.util.Map;

@Component(
        immediate = true,
        service = LibraryServiceFactoryBuilderFactory.class
)
public class LibraryServiceFactoryBuilderFactoryImpl implements LibraryServiceFactoryBuilderFactory {
    private EditIconService editIconService;

    @Override
    public ModelFactoryBuilder assetFactory() {
        return new AbstractModelFactoryBuilder() {

            @Override
            protected ModelFactory getFactoryInstance() {
                LibraryServiceFactory libraryServiceFactory = new LibraryServiceFactory();
                libraryServiceFactory.setLocale(getLocale());
                libraryServiceFactory.setRenderRequest(getRenderRequest());
                libraryServiceFactory.setRenderResponse(getRenderResponse());
                libraryServiceFactory.setTemplateNodes(getTemplateNodes());
                return libraryServiceFactory;
            }
        };
    }

    @Override
    public String getModelName() {
        return "Library Service";
    }

    class LibraryServiceFactory extends AbstractModelFactory {
        @Override
        public LibraryServiceModel newInstance(Map<String, Object> dataModel) {
            LibraryServiceModel libraryServiceModel = new LibraryServiceModel();
//            libraryServiceModel.setGroupBy(DynamicListModel.GroupBy.valueOf(getDataModelString(dataModel, "groupBy")));
//            libraryServiceModel.setOrderBy(DynamicListModel.OrderBy.valueOf(getDataModelString(dataModel, "orderBy")));
//            libraryServiceModel.setLimit(Integer.parseInt(getDataModelString(dataModel, "limit")));
//            libraryServiceModel.setQuery(getDataModelString(dataModel, "query"));
//            libraryServiceModel.setShowExtent(Boolean.parseBoolean(getDataModelString(dataModel, "showExtent")));
//            libraryServiceModel.setShowMediaclass(Boolean.parseBoolean(getDataModelString(dataModel, "showMediaClass")));
//            libraryServiceModel.setTitle(getDataModelString(dataModel, "reserved-article-title"));
//            libraryServiceModel.setCatalogueReferenceService(catalogueReferenceService);
            return libraryServiceModel;
        }

        @Override
        public LibraryServiceModel newInstance(AssetEntry assetEntry) throws Exception {
            JournalArticleAssetRenderer renderer = (JournalArticleAssetRenderer) assetEntry.getAssetRenderer();
            JournalArticle article = renderer.getArticle();
            Document docXml = SAXReaderUtil.read(article.getContentByLocale(getLocale().toString()));
            LibraryServiceModel libraryServiceModel = new LibraryServiceModel();
            injectEditIcon(editIconService, assetEntry, libraryServiceModel);
//            libraryServiceModel.setTitle(renderer.getTitle(getLocale()));
//            libraryServiceModel.setShowMediaclass(Boolean.parseBoolean(getElementValue(docXml, "showMediaClass")));
//            libraryServiceModel.setShowExtent(Boolean.parseBoolean(getElementValue(docXml, "showExtent")));
//            libraryServiceModel.setQuery(getElementValue(docXml, "query"));
//            libraryServiceModel.setLimit(Integer.parseInt(getElementValue(docXml, "limit")));
//            libraryServiceModel.setOrderBy(DynamicListModel.OrderBy.valueOf(getElementValue(docXml, "orderBy")));
//            libraryServiceModel.setGroupBy(DynamicListModel.GroupBy.valueOf(getElementValue(docXml, "groupBy")));
//            libraryServiceModel.setCatalogueReferenceService(catalogueReferenceService);
            return libraryServiceModel;
        }
    }

    @Reference
    protected void setEditIconService(EditIconService editIconService) {
        this.editIconService = editIconService;
    }
}
