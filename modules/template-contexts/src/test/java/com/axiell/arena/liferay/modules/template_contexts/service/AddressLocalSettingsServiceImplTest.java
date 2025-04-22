package com.axiell.arena.liferay.modules.template_contexts.service;

import com.liferay.portal.kernel.model.Address;
import com.liferay.portal.kernel.service.AddressLocalService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.mockito.Mockito.when;

public class AddressLocalSettingsServiceImplTest {

    private static final String ARENATEST_GMAIL_COM = "arenatest@gmail.com";
    private static final String API_KEY = "123456";

    @Mock
    AddressLocalService addressLocalServiceMock;

    @Mock
    Address addressMock;

    @BeforeEach
    void openMocks() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void getAddress_empty() {
        AddressLocalSettingsServiceImpl tested = new AddressLocalSettingsServiceImpl();
        tested.setAddressLocalService(addressLocalServiceMock);
        when(addressLocalServiceMock.getAddresses()).thenReturn(Collections.singletonList(addressMock));
        when(addressMock.getStreet1()).thenReturn("");
        when(addressMock.getCity()).thenReturn("");
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
        tested.setAddressLocalService(addressLocalServiceMock);
        when(addressLocalServiceMock.getAddresses()).thenReturn(Collections.singletonList(addressMock));
        when(addressMock.getStreet1()).thenReturn(API_KEY);
        when(addressMock.getCity()).thenReturn(ARENATEST_GMAIL_COM);
        assertThat(tested.getGoogleCalendarApiKey(), is(API_KEY));
        assertThat(tested.getGoogleCalendarId(), is(ARENATEST_GMAIL_COM));
    }

}
