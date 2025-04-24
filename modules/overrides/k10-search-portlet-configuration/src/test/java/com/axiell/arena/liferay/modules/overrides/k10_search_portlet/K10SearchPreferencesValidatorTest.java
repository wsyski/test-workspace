package com.axiell.arena.liferay.modules.overrides.k10_search_portlet;

import com.axiell.arena.liferay.modules.overrides.k10_search_portlet.constants.K10SearchPortletKeys;
import com.axiell.arena.liferay.modules.overrides.k10_search_portlet.portlet.action.K10SearchPreferencesValidator;
import com.liferay.petra.string.StringPool;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.portlet.PortletPreferences;
import javax.portlet.ValidatorException;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class K10SearchPreferencesValidatorTest {
    private final K10SearchPreferencesValidator underTest = new K10SearchPreferencesValidator();

    @Mock
    private PortletPreferences portletPreferences;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void validPageSizeValue() throws ValidatorException {
        BDDMockito.given(portletPreferences.getValue(K10SearchPortletKeys.KEY_PAGE_SIZE, StringPool.BLANK)).willReturn("10");
        underTest.validate(portletPreferences);
    }

    @Test
    public void invalidPageSizeValue() {
        BDDMockito.given(portletPreferences.getValue(K10SearchPortletKeys.KEY_PAGE_SIZE, StringPool.BLANK)).willReturn("0");
        assertThatThrownBy(() ->  underTest.validate(portletPreferences))
                .isInstanceOf(ValidatorException.class);
    }

    @Test
    public void nonNumericPageSizeValue() {
        BDDMockito.given(portletPreferences.getValue(K10SearchPortletKeys.KEY_PAGE_SIZE, StringPool.BLANK)).willReturn("string");
        assertThatThrownBy(() ->  underTest.validate(portletPreferences))
                .isInstanceOf(ValidatorException.class);
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
        assertThatThrownBy(() ->  underTest.validate(portletPreferences))
                .isInstanceOf(ValidatorException.class);
    }
}
