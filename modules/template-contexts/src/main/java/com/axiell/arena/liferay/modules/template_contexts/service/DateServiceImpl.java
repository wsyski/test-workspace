package com.axiell.arena.liferay.modules.template_contexts.service;

import org.osgi.service.component.annotations.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Locale;

@Component(
        immediate = true,
        service = DateService.class
)
public class DateServiceImpl implements DateService {
    @Override
    public LocalDate s2d(String date) {
        if (date != null && !date.isEmpty()) {
            try {
                return LocalDate.parse(date, DateTimeFormatter.ISO_LOCAL_DATE);
            } catch (DateTimeParseException ignored) {
                return LocalDate.parse(date, DateTimeFormatter.RFC_1123_DATE_TIME);
            }
        }
        return null;
    }

    @Override
    public String d2s(LocalDate date, Locale locale) {
        return date == null ? "" : date.format(DateTimeFormatter.ISO_LOCAL_DATE.withLocale(locale));
    }
}
