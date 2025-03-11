package com.axiell.arena.liferay.modules.template_contexts.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.osgi.service.component.annotations.Component;
import org.springframework.web.client.RestTemplate;

@Component(service = TemplateContextConfig.class)
public class TemplateContextContextConfigImpl implements TemplateContextConfig {

    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }
}
