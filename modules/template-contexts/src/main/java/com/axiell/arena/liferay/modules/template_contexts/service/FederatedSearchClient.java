package com.axiell.arena.liferay.modules.template_contexts.service;

import com.axiell.arena.liferay.modules.template_contexts.model.RecordsResponse;

import java.util.List;

public interface FederatedSearchClient {

    RecordsResponse getRecords(String sourceId,
                               Integer start,
                               Integer size,
                               Integer facetSize,
                               String q,
                               List<String> c,
                               List<String> fc,
                               List<String> facetField,
                               List<String> sort) throws Exception;

    RecordsResponse getRecords(String sourceId,
                               Integer start,
                               Integer size,
                               Integer facetSize,
                               String q,
                               String c,
                               String fc,
                               String facetField,
                               String sort) throws Exception;

    RecordsResponse getRecordsByLMSSearchIds(String query, String type, Integer start, Integer size) throws Exception;

    RecordsResponse getRecordsByAlmaRecordIds(String query, Integer start, Integer size);
}
