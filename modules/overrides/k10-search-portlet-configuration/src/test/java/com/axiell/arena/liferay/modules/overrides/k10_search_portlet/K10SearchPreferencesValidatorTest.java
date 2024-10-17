package com.axiell.arena.liferay.modules.overrides.k10_search_portlet;

import com.axiell.arena.liferay.modules.overrides.k10_search_portlet.constants.K10SearchPortletKeys;
import com.axiell.arena.liferay.modules.overrides.k10_search_portlet.portlet.action.K10SearchPreferencesValidator;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import com.liferay.petra.string.StringPool;

import javax.portlet.PortletPreferences;
import javax.portlet.ValidatorException;

@RunWith(MockitoJUnitRunner.class)
public class K10SearchPreferencesValidatorTest {
    private final K10SearchPreferencesValidator underTest = new K10SearchPreferencesValidator();

    @Mock
    private PortletPreferences portletPreferences;

    @Test
    public void validPageSizeValue() throws ValidatorException {
        BDDMockito.given(portletPreferences.getValue(K10SearchPortletKeys.KEY_PAGE_SIZE, StringPool.BLANK)).willReturn("10");
        underTest.validate(portletPreferences);
    }

    @Test(expected = ValidatorException.class)
    public void invalidPageSizeValue() throws ValidatorException {
        BDDMockito.given(portletPreferences.getValue(K10SearchPortletKeys.KEY_PAGE_SIZE, StringPool.BLANK)).willReturn("0");
        underTest.validate(portletPreferences);
    }

    @Test(expected = ValidatorException.class)
    public void nonNumericPageSizeValue() throws ValidatorException {
        BDDMockito.given(portletPreferences.getValue(K10SearchPortletKeys.KEY_PAGE_SIZE, StringPool.BLANK)).willReturn("string");
        underTest.validate(portletPreferences);
    }

    @Test
    public void validEventDetailPageValue() throws ValidatorException {
        BDDMockito.given(portletPreferences.getValue(K10SearchPortletKeys.KEY_PAGE_SIZE, StringPool.BLANK)).willReturn("10");
        BDDMockito.given(portletPreferences.getValue(K10SearchPortletKeys.KEY_K10_SEARCH_PAGE, StringPool.BLANK)).willReturn("event-detail");
        underTest.validate(portletPreferences);
    }

    @Test(expected = ValidatorException.class)
    public void invalidEventDetailPageValue() throws ValidatorException {
        BDDMockito.given(portletPreferences.getValue(K10SearchPortletKeys.KEY_PAGE_SIZE, StringPool.BLANK)).willReturn("10");
        BDDMockito.given(portletPreferences.getValue(K10SearchPortletKeys.KEY_K10_SEARCH_PAGE, StringPool.BLANK)).willReturn("/event-detail");
        underTest.validate(portletPreferences);
    }
}
