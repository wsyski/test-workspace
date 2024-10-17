package com.axiell.arena.liferay.modules.template_contexts.model;

import com.axiell.arena.liferay.modules.template_contexts.api.ModelFactory;
import com.axiell.arena.liferay.modules.template_contexts.api.ModelFactoryBuilder;
import com.axiell.arena.liferay.modules.template_contexts.api.StaffPickFactoryBuilderFactory;
import com.axiell.arena.liferay.modules.template_contexts.service.CatalogueReferenceService;
import com.axiell.arena.liferay.modules.template_contexts.service.DateService;
import com.axiell.arena.liferay.modules.template_contexts.service.EditIconService;
import com.axiell.arena.liferay.modules.template_contexts.service.StaticContextService;
import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.web.internal.asset.model.JournalArticleAssetRenderer;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.SAXReaderUtil;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Map;
import java.util.concurrent.ExecutionException;

@Component(
        immediate = true,
        service = StaffPickFactoryBuilderFactory.class
)
public class StaffPickFactoryBuilderFactoryImpl implements StaffPickFactoryBuilderFactory {

    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");

    private DateService dateService;
    private CatalogueReferenceService catalogueReferenceService;
    private EditIconService editIconService;
    private StaticContextService staticContextService;

    @Override
    public ModelFactoryBuilder assetFactory() {
        return new AbstractModelFactoryBuilder() {

            @Override
            protected ModelFactory getFactoryInstance() {
                StaffPickFactory staffPickFactory = new StaffPickFactory();
                staffPickFactory.setLocale(getLocale());
                staffPickFactory.setRenderRequest(getRenderRequest());
                staffPickFactory.setRenderResponse(getRenderResponse());
                staffPickFactory.setTemplateNodes(getTemplateNodes());
                return staffPickFactory;
            }
        };
    }

    @Override
    public String getModelName() {
        return "Staff pick";
    }

    class StaffPickFactory extends AbstractModelFactory {

        @Override
        public StaffPickModel newInstance(Map<String, Object> dataModel) throws IOException, ExecutionException {
            String smallImageUrl = getDataModelString(dataModel, "reserved-article-small-image-url");
            StaffPickModel staffPickDto = new StaffPickModel();
            staffPickDto.setImage(smallImageUrl.equals("") ? null : smallImageUrl);
            staffPickDto.setArticleTitle(getDataModelString(dataModel, "reserved-article-title"));
            staffPickDto.setArticleAuthor(getDataModelString(dataModel, "articleAuthor"));
            staffPickDto.setArticleText(getDataModelString(dataModel, "articleText"));
            staffPickDto.setSummary(getDataModelString(dataModel, "reserved-article-description"));
            staffPickDto.setDisplayDate(dateService.s2d(getDataModelString(dataModel, "reserved-article-display-date")));
            staffPickDto.setArticleId(getDataModelString(dataModel, "reserved-article-id"));
            String recordId = getTrimmedRecordId(getDataModelString(dataModel, "recordId"));
            staffPickDto.setRecordId(recordId);
            staffPickDto.setRecord(catalogueReferenceService.getRecord(Long.parseLong(recordId), (Long) dataModel.get("siteGroupId")));
            staffPickDto.setCatalogueReferenceService(catalogueReferenceService);
            staffPickDto.setDateService(dateService);
            return staffPickDto;
        }

        @Override
        public StaffPickModel newInstance(AssetEntry assetEntry) throws Exception {
            JournalArticleAssetRenderer renderer = (JournalArticleAssetRenderer) assetEntry.getAssetRenderer();
            JournalArticle article = renderer.getArticle();
            Document docXml = SAXReaderUtil.read(article.getContentByLocale(getLocale().toString()));
            StaffPickModel staffPickDto = new StaffPickModel();
            injectEditIcon(editIconService, assetEntry, staffPickDto);
            injectViewUrl(staticContextService, assetEntry, staffPickDto);
            staffPickDto.setArticleAuthor(getElementValue(docXml, "articleAuthor"));
            String recordId = getTrimmedRecordId(getElementValue(docXml, "recordId"));
            staffPickDto.setRecordId(recordId);
            staffPickDto.setDisplayDate(dateService.s2d(DATE_FORMAT.format(renderer.getArticle().getDisplayDate())));
            staffPickDto.setArticleTitle(renderer.getTitle(getLocale()));
            staffPickDto.setImage(renderer.getThumbnailPath(getRenderRequest()));
            staffPickDto.setArticleId(renderer.getArticle().getArticleId());
            staffPickDto.setSummary(article.getDescription(getLocale()));
            staffPickDto.setRecord(catalogueReferenceService.getRecord(Long.parseLong(recordId), assetEntry.getGroupId()));
            staffPickDto.setCatalogueReferenceService(catalogueReferenceService);
            staffPickDto.setDateService(dateService);
            return staffPickDto;
        }

        String getTrimmedRecordId(String recordId) {
            String trimmedRecordId = recordId;
            if (recordId.startsWith("id:")) {
                trimmedRecordId = recordId.substring(3);
            }
            return trimmedRecordId;
        }
    }

    @Reference
    protected void setDateService(DateService dateService) {
        this.dateService = dateService;
    }

    @Reference
    protected void setCatalogueReferenceService(CatalogueReferenceService catalogueReferenceService) {
        this.catalogueReferenceService = catalogueReferenceService;
    }

    @Reference
    protected void setEditIconService(EditIconService editIconService) {
        this.editIconService = editIconService;
    }

    @Reference
    protected void setStaticContextService(StaticContextService staticContextService) {
        this.staticContextService = staticContextService;
    }
}