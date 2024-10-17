package com.axiell.arena.liferay.modules.overrides.k10_search_portlet;

import com.axiell.arena.liferay.modules.overrides.k10_search_portlet.constants.K10SearchPortletKeys;
import com.axiell.arena.liferay.modules.overrides.k10_search_portlet.portlet.action.K10SearchPreferencesValidator;
import com.liferay.petra.string.StringPool;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.mockito.Mock;

import javax.portlet.PortletPreferences;
import javax.portlet.ValidatorException;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class K10SearchPreferencesValidatorTest {
    private final K10SearchPreferencesValidator underTest = new K10SearchPreferencesValidator();

    @Mock
    private PortletPreferences portletPreferences;

    @Test
    public void validPageSizeValue() throws ValidatorException {
        BDDMockito.given(portletPreferences.getValue(K10SearchPortletKeys.KEY_PAGE_SIZE, StringPool.BLANK)).willReturn("10");
        underTest.validate(portletPreferences);
    }

    @Test
    public void invalidPageSizeValue() {
        BDDMockito.given(portletPreferences.getValue(K10SearchPortletKeys.KEY_PAGE_SIZE, StringPool.BLANK)).willReturn("0");
        ValidatorException thrown = assertThrows(
                ValidatorException.class,
                () -> underTest.validate(portletPreferences),
                "Expected ValidatorException to be thrown"
        );
    }

    @Test
    public void nonNumericPageSizeValue() throws ValidatorException {
        BDDMockito.given(portletPreferences.getValue(K10SearchPortletKeys.KEY_PAGE_SIZE, StringPool.BLANK)).willReturn("string");
        ValidatorException thrown = assertThrows(
                ValidatorException.class,
                () -> underTest.validate(portletPreferences),
                "Expected ValidatorException to be thrown"
        );
    }

    @Test
    public void validEventDetailPageValue() throws ValidatorException {
        BDDMockito.given(portletPreferences.getValue(K10SearchPortletKeys.KEY_PAGE_SIZE, StringPool.BLANK)).willReturn("10");
        BDDMockito.given(portletPreferences.getValue(K10SearchPortletKeys.KEY_K10_SEARCH_PAGE, StringPool.BLANK)).willReturn("event-detail");
        underTest.validate(portletPreferences);
    }

    @Test
    public void invalidEventDetailPageValue() {
        BDDMockito.given(portletPreferences.getValue(K10SearchPortletKeys.KEY_PAGE_SIZE, StringPool.BLANK)).willReturn("10");
        BDDMockito.given(portletPreferences.getValue(K10SearchPortletKeys.KEY_K10_SEARCH_PAGE, StringPool.BLANK)).willReturn("/event-detail");
        ValidatorException thrown = assertThrows(
                ValidatorException.class,
                () -> underTest.validate(portletPreferences),
                "Expected ValidatorException to be thrown"
        );
    }
}
