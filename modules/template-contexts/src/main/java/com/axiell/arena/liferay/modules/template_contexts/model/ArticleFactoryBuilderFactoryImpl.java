package com.axiell.arena.liferay.modules.template_contexts.model;

import com.axiell.arena.liferay.modules.template_contexts.api.ArticleFactoryBuilderFactory;
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
        service = ArticleFactoryBuilderFactory.class
)
public class ArticleFactoryBuilderFactoryImpl implements ArticleFactoryBuilderFactory {
    private EditIconService editIconService;

    @Override
    public ModelFactoryBuilder assetFactory() {
        return new AbstractModelFactoryBuilder() {

            @Override
            protected ModelFactory getFactoryInstance() {
                ArticleFactory articleFactory = new ArticleFactory();
                articleFactory.setLocale(getLocale());
                articleFactory.setRenderRequest(getRenderRequest());
                articleFactory.setRenderResponse(getRenderResponse());
                articleFactory.setTemplateNodes(getTemplateNodes());
                return articleFactory;
            }
        };
    }

    @Override
    public String getModelName() {
        return "Article";
    }

    class ArticleFactory extends AbstractModelFactory {
        @Override
        public ArticleModel newInstance(Map<String, Object> dataModel) {
            ArticleModel articleModel = new ArticleModel();
//            articleModel.setGroupBy(DynamicListModel.GroupBy.valueOf(getDataModelString(dataModel, "groupBy")));
//            articleModel.setOrderBy(DynamicListModel.OrderBy.valueOf(getDataModelString(dataModel, "orderBy")));
//            articleModel.setLimit(Integer.parseInt(getDataModelString(dataModel, "limit")));
//            articleModel.setQuery(getDataModelString(dataModel, "query"));
//            articleModel.setShowExtent(Boolean.parseBoolean(getDataModelString(dataModel, "showExtent")));
//            articleModel.setShowMediaclass(Boolean.parseBoolean(getDataModelString(dataModel, "showMediaClass")));
//            articleModel.setTitle(getDataModelString(dataModel, "reserved-article-title"));
//            articleModel.setCatalogueReferenceService(catalogueReferenceService);
            return articleModel;
        }

        @Override
        public ArticleModel newInstance(AssetEntry assetEntry) throws Exception {
            JournalArticleAssetRenderer renderer = (JournalArticleAssetRenderer) assetEntry.getAssetRenderer();
            JournalArticle article = renderer.getArticle();
            Document docXml = SAXReaderUtil.read(article.getContentByLocale(getLocale().toString()));
            ArticleModel articleModel = new ArticleModel();
            injectEditIcon(editIconService, assetEntry, articleModel);
//            articleModel.setTitle(renderer.getTitle(getLocale()));
//            articleModel.setShowMediaclass(Boolean.parseBoolean(getElementValue(docXml, "showMediaClass")));
//            articleModel.setShowExtent(Boolean.parseBoolean(getElementValue(docXml, "showExtent")));
//            articleModel.setQuery(getElementValue(docXml, "query"));
//            articleModel.setLimit(Integer.parseInt(getElementValue(docXml, "limit")));
//            articleModel.setOrderBy(DynamicListModel.OrderBy.valueOf(getElementValue(docXml, "orderBy")));
//            articleModel.setGroupBy(DynamicListModel.GroupBy.valueOf(getElementValue(docXml, "groupBy")));
//            articleModel.setCatalogueReferenceService(catalogueReferenceService);
            return articleModel;
        }
    }

    @Reference
    protected void setEditIconService(EditIconService editIconService) {
        this.editIconService = editIconService;
    }

}
