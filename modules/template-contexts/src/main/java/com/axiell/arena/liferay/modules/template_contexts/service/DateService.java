package com.axiell.arena.liferay.modules.template_contexts.service;

import java.time.LocalDate;
import java.util.Locale;

public interface DateService {
    LocalDate s2d(String date);

    String d2s(LocalDate date, Locale locale);
}
