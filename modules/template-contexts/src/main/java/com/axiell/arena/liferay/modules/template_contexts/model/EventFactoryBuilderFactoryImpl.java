package com.axiell.arena.liferay.modules.template_contexts.model;

import com.axiell.arena.liferay.modules.template_contexts.api.EventFactoryBuilderFactory;
import com.axiell.arena.liferay.modules.template_contexts.api.ModelFactory;
import com.axiell.arena.liferay.modules.template_contexts.api.ModelFactoryBuilder;
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

import java.util.Map;

@Component(
        immediate = true,
        service = EventFactoryBuilderFactory.class
)
public class EventFactoryBuilderFactoryImpl implements EventFactoryBuilderFactory {

    private DateService dateService;
    private EditIconService editIconService;
    private StaticContextService staticContextService;

    @Override
    public ModelFactoryBuilder assetFactory() {
        return new AbstractModelFactoryBuilder() {

            @Override
            protected ModelFactory getFactoryInstance() {
                EventFactory eventFactory = new EventFactory();
                eventFactory.setLocale(getLocale());
                eventFactory.setRenderRequest(getRenderRequest());
                eventFactory.setRenderResponse(getRenderResponse());
                eventFactory.setTemplateNodes(getTemplateNodes());
                return eventFactory;
            }
        };
    }

    @Override
    public String getModelName() {
        return "Event";
    }

    class EventFactory extends AbstractModelFactory {
        @Override
        @SuppressWarnings("unchecked")
        public EventModel newInstance(Map<String, Object> dataModel) {
            EventModel eventDto = new EventModel();
            eventDto.setTitle(getDataModelString(dataModel, "reserved-article-title"));
            eventDto.setEventDate(dateService.s2d(getDataModelString(dataModel, "eventDate")));
            eventDto.setEventEndDate(dateService.s2d(getDataModelString(dataModel, "eventEndDate")));
            eventDto.setEventTime(getDataModelString(dataModel, "eventTime"));
            eventDto.setEventLocation(getDataModelString(dataModel, "eventLocation"));
            eventDto.setLocationAddress(getDataModelString(dataModel, "locationAddress"));
            eventDto.setAltText(getDataValue(((Map<String, Object>) dataModel.get("documentsAndMediaImage")).get("eventImgAlt")));
            eventDto.setEventText(getDataModelString(dataModel, "eventText"));
            eventDto.setImage(getDataModelString(dataModel, "documentsAndMediaImage"));
            eventDto.setDateService(dateService);
            return eventDto;
        }

        @Override
        public EventModel newInstance(AssetEntry assetEntry) throws Exception {
            JournalArticleAssetRenderer renderer = (JournalArticleAssetRenderer) assetEntry.getAssetRenderer();
            JournalArticle article = renderer.getArticle();
            Document docXml = SAXReaderUtil.read(article.getContentByLocale(getLocale().toString()));
            EventModel eventDto = new EventModel();
            injectEditIcon(editIconService, assetEntry, eventDto);
            injectViewUrl(staticContextService, assetEntry, eventDto);
            eventDto.setTitle(renderer.getTitle(getLocale()));
            eventDto.setEventDate(dateService.s2d(getElementValue(docXml, "eventDate")));
            eventDto.setEventEndDate(dateService.s2d(getElementValue(docXml, "eventEndDate")));
            eventDto.setEventTime(getElementValue(docXml, "eventTime"));
            eventDto.setEventLocation(getElementValue(docXml, "eventLocation"));
            eventDto.setAltText(docXml.valueOf("//dynamic-element[@name='documentsAndMediaImage']/dynamic-element[@name='eventImgAlt']/dynamic-content/text()"));
            eventDto.setImage(getElementValue(docXml, "documentsAndMediaImage"));
            eventDto.setDateService(dateService);
            return eventDto;
        }
    }

    @Reference
    protected void setEditIconService(EditIconService editIconService) {
        this.editIconService = editIconService;
    }

    @Reference
    protected void setDateService(DateService dateService) {
        this.dateService = dateService;
    }

    @Reference
    protected void setStaticContextService(StaticContextService staticContextService) {
        this.staticContextService = staticContextService;
    }
}
