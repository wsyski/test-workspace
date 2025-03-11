package com.axiell.arena.liferay.modules.template_contexts.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.client.RestTemplate;

public interface TemplateContextConfig {

    RestTemplate restTemplate();

    ObjectMapper objectMapper();
}
