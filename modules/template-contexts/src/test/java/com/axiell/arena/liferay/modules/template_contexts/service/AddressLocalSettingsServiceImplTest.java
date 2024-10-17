package com.axiell.arena.liferay.modules.template_contexts.service;

import com.liferay.portal.kernel.model.Address;
import com.liferay.portal.kernel.service.AddressLocalService;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.util.Collections;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.mockito.Mockito.when;

public class AddressLocalSettingsServiceImplTest {

    private static final String ARENATEST_GMAIL_COM = "arenatest@gmail.com";
    private static final String API_KEY = "123456";

    @Mock
    AddressLocalService addressLocalService;

    @Mock
    Address address;

    @Test
    public void getAddress_empty() {
        AddressLocalSettingsServiceImpl tested = new AddressLocalSettingsServiceImpl();
        tested.setAddressLocalService(addressLocalService);
        Address address = tested.getAddress();
        assertThat(address, is(notNullValue()));
        assertThat(address.getStreet1(), is(""));
        assertThat(address.getCity(), is(""));
        assertThat(tested.getGoogleCalendarApiKey(), is(""));
        assertThat(tested.getGoogleCalendarId(), is(""));
    }

    @Test
    public void getters() {
        AddressLocalSettingsServiceImpl tested = new AddressLocalSettingsServiceImpl();
        tested.setAddressLocalService(addressLocalService);
        when(addressLocalService.getAddresses()).thenReturn(Collections.singletonList(address));
        when(address.getStreet1()).thenReturn(API_KEY);
        when(address.getCity()).thenReturn(ARENATEST_GMAIL_COM);
        assertThat(tested.getGoogleCalendarApiKey(), is(API_KEY));
        assertThat(tested.getGoogleCalendarId(), is(ARENATEST_GMAIL_COM));
    }

}
