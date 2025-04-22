package com.axiell.arena.liferay.modules.arena;

import lombok.extern.java.Log;
import org.junit.jupiter.api.Test;
import java.util.Calendar;
import java.util.Locale;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

@Log
public class LocaleTest {

    @Test
    public void firstDayOfTheWeek() {
        Locale locale = new Locale("en", "GB");
        Calendar calobj = Calendar.getInstance(locale);
        System.out.println("First day of the week: " + calobj.getFirstDayOfWeek());
        assertThat(calobj.getFirstDayOfWeek(), is(2));
    }
}
