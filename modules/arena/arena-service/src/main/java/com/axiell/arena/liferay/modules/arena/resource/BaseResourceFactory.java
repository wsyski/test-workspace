package com.axiell.arena.liferay.modules.arena.resource;

import com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider;
import org.apache.cxf.jaxrs.client.JAXRSClientFactory;
import org.apache.cxf.jaxrs.client.WebClient;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.axiell.authinfo.IAuthHeaderParser.BEARER_SCHEME;

public class BaseResourceFactory {
    private final List<Object> clientProviders = new ArrayList<>();

    public BaseResourceFactory() {
        this.clientProviders.add(new JacksonJsonProvider());
    }

    public List<Object> getClientProviders() {
        return clientProviders;
    }

    public <T> T createResource(final String apiEndpoint, final Class<T> clazz) {
        T proxy = JAXRSClientFactory.create(apiEndpoint, clazz, Collections.singletonList(clientProviders));
        return proxy;
    }
}
