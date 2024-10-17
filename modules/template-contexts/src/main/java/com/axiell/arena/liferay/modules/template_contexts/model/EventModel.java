package com.axiell.arena.liferay.modules.template_contexts.model;

import com.axiell.arena.liferay.modules.template_contexts.service.DateService;
import org.osgi.service.component.annotations.Reference;

import java.time.LocalDate;
import java.util.Locale;

public class EventModel extends AbstractImageModel {

    private DateService dateService;
    private String title;
    private LocalDate eventDate;
    private LocalDate eventEndDate;
    private String eventTime;
    private String eventLocation;
    private String locationAddress;
    private String eventText;

    public boolean hasEventEndDate() {
        return eventEndDate != null;
    }

    public boolean hasEventText() {
        return eventText != null && !eventText.equals("");
    }

    public String getEventText() {
        return eventText;
    }

    void setEventText(String eventText) {
        this.eventText = eventText;
    }

    public String getLocationAddress() {
        return locationAddress;
    }

    void setLocationAddress(String locationAddress) {
        this.locationAddress = locationAddress;
    }

    public String getTitle() {
        return title;
    }

    void setTitle(String title) {
        this.title = title;
    }

    public String getEventDate(Locale locale) {
        return dateService.d2s(eventDate, locale);
    }

    void setEventDate(LocalDate eventDate) {
        this.eventDate = eventDate;
    }

    public String getEventEndDate(Locale locale) {
        return dateService.d2s(eventEndDate, locale);
    }

    void setEventEndDate(LocalDate eventEndDate) {
        this.eventEndDate = eventEndDate;
    }

    public String getEventTime() {
        return eventTime;
    }

    void setEventTime(String eventTime) {
        this.eventTime = eventTime;
    }

    public String getEventLocation() {
        return eventLocation;
    }

    void setEventLocation(String eventLocation) {
        this.eventLocation = eventLocation;
    }

    @Reference
    protected void setDateService(DateService dateService) {
        this.dateService = dateService;
    }
}
