package com.axiell.arena.liferay.modules.template_contexts.model;

import com.axiell.arena.liferay.modules.template_contexts.api.ModelFactory;
import com.axiell.arena.liferay.modules.template_contexts.api.ModelFactoryBuilder;
import com.axiell.arena.liferay.modules.template_contexts.api.ThemedSelectionFactoryBuilderFactory;
import com.axiell.arena.liferay.modules.template_contexts.service.CatalogueReferenceService;
import com.axiell.arena.liferay.modules.template_contexts.service.EditIconService;
import com.axiell.arena.liferay.modules.template_contexts.service.StaticContextService;
import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.web.internal.asset.model.JournalArticleAssetRenderer;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.SAXReaderUtil;
import org.apache.commons.lang3.StringUtils;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.lang.Boolean.parseBoolean;
import static java.lang.Integer.parseInt;

@Component(
        immediate = true,
        service = ThemedSelectionFactoryBuilderFactory.class
)
public class ThemedSelectionFactoryBuilderFactoryImpl implements ThemedSelectionFactoryBuilderFactory {

    private EditIconService editIconService;
    private CatalogueReferenceService catalogueReferenceService;
    private StaticContextService staticContextService;

    @Override
    public ModelFactoryBuilder assetFactory() {
        return new AbstractModelFactoryBuilder() {

            @Override
            protected ModelFactory getFactoryInstance() {
                ThemedSelectionFactory themedSelectionFactory = new ThemedSelectionFactory();
                themedSelectionFactory.setLocale(getLocale());
                themedSelectionFactory.setRenderRequest(getRenderRequest());
                themedSelectionFactory.setRenderResponse(getRenderResponse());
                themedSelectionFactory.setTemplateNodes(getTemplateNodes());
                return themedSelectionFactory;
            }
        };
    }

    @Override
    public String getModelName() {
        return "Themed Selection";
    }

    class ThemedSelectionFactory extends AbstractModelFactory {

        @Override
        @SuppressWarnings("unchecked")
        public ThemedSelectionModel newInstance(Map<String, Object> dataModel) {
            ThemedSelectionModel themedSelectionDto = new ThemedSelectionModel();
            themedSelectionDto.setTitle(getDataModelString(dataModel, "reserved-article-title"));
            themedSelectionDto.setSummary(getDataModelString(dataModel, "reserved-article-description"));
            themedSelectionDto.setImage(getDataModelString(dataModel, "image"));
            Map<Object, Object> image = (Map<Object, Object>) dataModel.get("image");
            Map<Object, Object> attributes = (Map<Object, Object>) image.get("attributes");
            themedSelectionDto.setAltText((String) attributes.get("alt"));
            List<DynamicListModel> dynamicListDtos = getTemplateNodes().stream().map(templateNode -> {
                DynamicListModel dynamicListDto = new DynamicListModel();
                dynamicListDto.setLimit(getTemplateNodeInteger(templateNode, "limit"));
                dynamicListDto.setQuery(getTemplateNodeString(templateNode, "query"));
                dynamicListDto.setShowMediaclass(getTemplateNodeBoolean(templateNode, "showMediaClass"));
                dynamicListDto.setShowExtent(getTemplateNodeBoolean(templateNode, "showExtent"));
                String groupBy = getTemplateNodeString(templateNode, "groupBy");
                dynamicListDto.setTitle(getTemplateNodeString(templateNode, "title"));
                dynamicListDto.setCatalogueReferenceService(catalogueReferenceService);
                return dynamicListDto;
            }).collect(Collectors.toList());
            themedSelectionDto.setDynamicLists(dynamicListDtos);
            return themedSelectionDto;
        }

        @Override
        public ThemedSelectionModel newInstance(AssetEntry assetEntry) throws Exception {
            JournalArticleAssetRenderer renderer = (JournalArticleAssetRenderer) assetEntry.getAssetRenderer();
            JournalArticle article = renderer.getArticle();
            Document docXml = SAXReaderUtil.read(article.getContentByLocale(getLocale().toString()));
            ThemedSelectionModel themedSelectionDto = new ThemedSelectionModel();
            injectEditIcon(editIconService, assetEntry, themedSelectionDto);
            injectViewUrl(staticContextService, assetEntry, themedSelectionDto);
            themedSelectionDto.setAltText(docXml.valueOf("//dynamic-element[@name='image']/dynamic-content/@alt"));
            themedSelectionDto.setImage(getElementValue(docXml, "image"));
            themedSelectionDto.setTitle(renderer.getTitle(getLocale()));
            List<DynamicListModel> dynamicListDtos = docXml.selectNodes("//dynamic-element[@name='dynamicLists']")
                    .stream().map(dl -> {
                        DynamicListModel dynamicListDto = new DynamicListModel();
                        dynamicListDto.setTitle(getElementValue(dl, "title"));
                        dynamicListDto.setQuery(getElementValue(dl, "query"));
                        dynamicListDto.setLimit(parseInt(getElementValue(dl, "limit")));
                        String showMediaClass = getElementValue(dl, "showMediaClass");
                        if (StringUtils.isNotBlank(showMediaClass)) {
                            dynamicListDto.setShowMediaclass(Boolean.parseBoolean(showMediaClass));
                        }
                        String showExtent = getElementValue(dl, "showExtent");
                        if (StringUtils.isNotBlank(showExtent)) {
                            dynamicListDto.setShowExtent(Boolean.parseBoolean(showExtent));
                        }
                        dynamicListDto.setCatalogueReferenceService(catalogueReferenceService);
                        return dynamicListDto;
                    }).collect(Collectors.toList());
            themedSelectionDto.setDynamicLists(dynamicListDtos);
            return themedSelectionDto;

        }
    }

    @Reference
    protected void setEditIconService(EditIconService editIconService) {
        this.editIconService = editIconService;
    }

    @Reference
    protected void setCatalogueReferenceService(CatalogueReferenceService catalogueReferenceService) {
        this.catalogueReferenceService = catalogueReferenceService;
    }

    @Reference
    protected void setStaticContextService(StaticContextService staticContextService) {
        this.staticContextService = staticContextService;
    }
}
