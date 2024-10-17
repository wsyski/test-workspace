package com.axiell.arena.liferay.modules.template_contexts.model;

import com.axiell.arena.liferay.modules.template_contexts.api.FeaturedSearchFactoryBuilderFactory;
import com.axiell.arena.liferay.modules.template_contexts.api.ModelFactory;
import com.axiell.arena.liferay.modules.template_contexts.api.ModelFactoryBuilder;
import com.axiell.arena.liferay.modules.template_contexts.service.CatalogueReferenceService;
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
        service = FeaturedSearchFactoryBuilderFactory.class
)
public class FeaturedSearchFactoryBuilderFactoryImpl implements FeaturedSearchFactoryBuilderFactory {
    private CatalogueReferenceService catalogueReferenceService;
    private EditIconService editIconService;

    @Override
    public ModelFactoryBuilder assetFactory() {
        return new AbstractModelFactoryBuilder() {

            @Override
            protected ModelFactory getFactoryInstance() {
                FeaturedSearchFactory featuredSearchFactory = new FeaturedSearchFactory();
                featuredSearchFactory.setLocale(getLocale());
                featuredSearchFactory.setRenderRequest(getRenderRequest());
                featuredSearchFactory.setRenderResponse(getRenderResponse());
                featuredSearchFactory.setTemplateNodes(getTemplateNodes());
                return featuredSearchFactory;
            }
        };

    }

    @Override
    public String getModelName() {
        return "Featured Search";
    }

    class FeaturedSearchFactory extends AbstractModelFactory {
        // This method is never used as there is not detailed view for this entity
        @Override
        @SuppressWarnings("unchecked")
        public FeaturedSearchModel newInstance(Map<String, Object> dataModel) {
            FeaturedSearchModel featuredSearchDto = new FeaturedSearchModel();
            featuredSearchDto.setImage(getDataModelString(dataModel, "image"));
            Map<Object, Object> image = (Map<Object, Object>) dataModel.get("image");
            Map<Object, Object> attributes = (Map<Object, Object>) image.get("attributes");
            featuredSearchDto.setAltText((String) attributes.get("alt"));
            String query = getDataModelString(dataModel, "query");
            featuredSearchDto.setQuery(query);
            featuredSearchDto.setTitle(getDataModelString(dataModel, "reserved-article-title"));
            featuredSearchDto.setCatalogueReferenceService(catalogueReferenceService);
            return featuredSearchDto;
        }

        @Override
        public FeaturedSearchModel newInstance(AssetEntry assetEntry) throws Exception {
            JournalArticleAssetRenderer renderer = (JournalArticleAssetRenderer) assetEntry.getAssetRenderer();
            JournalArticle article = renderer.getArticle();
            Document docXml = SAXReaderUtil.read(article.getContentByLocale(getLocale().toString()));
            FeaturedSearchModel featuredSearchDto = new FeaturedSearchModel();
            injectEditIcon(editIconService, assetEntry, featuredSearchDto);
            featuredSearchDto.setAltText(docXml.valueOf("//dynamic-element[@name='image']/dynamic-content/@alt"));
            featuredSearchDto.setImage(getElementValue(docXml, "image"));
            featuredSearchDto.setTitle(renderer.getTitle(getLocale()));
            String query = getElementValue(docXml, "query");
            featuredSearchDto.setQuery(query);
            featuredSearchDto.setCatalogueReferenceService(catalogueReferenceService);
            return featuredSearchDto;
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
