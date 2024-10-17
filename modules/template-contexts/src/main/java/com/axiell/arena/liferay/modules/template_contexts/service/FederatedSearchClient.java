package com.axiell.arena.liferay.modules.template_contexts.service;

import com.axiell.arena.liferay.modules.template_contexts.model.RecordsResponse;

public interface FederatedSearchClient {

    RecordsResponse getRecords(Integer start, Integer size, String params) throws Exception;

    RecordsResponse getRecords(String sourceId, Integer start, Integer size, String params) throws Exception;

    RecordsResponse getRecords(String sourceId,
                               Integer start,
                               Integer size,
                               String params,
                               Integer facetSize,
                               Boolean nativeQuery,
                               String q,
                               String c,
                               String sort) throws Exception;

    RecordsResponse getRecordsByLMSSearchIds(String query, String type, Integer start, Integer size) throws Exception;
}
