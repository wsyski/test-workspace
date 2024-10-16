package com.axiell.arena.liferay.modules.arena;

import org.junit.Test;

import java.util.Calendar;
import java.util.Locale;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class LocaleTest {

    @Test
    public void firstDayOfTheWeek() {
        Locale locale = new Locale("en", "GB");
        Calendar calobj = Calendar.getInstance(locale);
        assertThat(calobj.getFirstDayOfWeek(), is(2));
    }
}
