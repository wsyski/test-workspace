package com.axiell.arena.liferay.modules.template_contexts.service;

import com.axiell.arena.liferay.modules.arena.configuration.ArenaGroupConfiguration;
import com.axiell.arena.liferay.modules.arena.configuration.ArenaSystemConfiguration;
import com.axiell.arena.liferay.modules.arena.model.LMSSearchResponse;
import com.axiell.arena.liferay.modules.arena.model.LMSSearchResultResponse;
import com.axiell.arena.liferay.modules.arena.util.ArenaUtil;
import com.axiell.arena.liferay.modules.template_contexts.model.FedEventResponse;
import com.axiell.arena.liferay.modules.template_contexts.model.RecordsResponse;
import com.axiell.arena.liferay.modules.template_contexts.model.SearchCriteriaDto;
import com.axiell.arena.liferay.modules.template_contexts.model.central.CentralSettingsDto;
import lombok.CustomLog;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@CustomLog
@Component(service = FederatedSearchClient.class)
public class FederatedSearchClientImpl implements FederatedSearchClient {

    private static final String ALMA_ID_FIELD = "almaId";
    private static final String SCORE_SORT_FIELD = "score";
    private static final String SEARCH_EVENT = "event:search";
    private static final String DATA_PREFIX = "data:";
    private static final String EVENT_SEPARATOR = "\n\n";
    @Reference
    private ArenaLocalServiceClient arenaLocalServiceClient;
    @Reference
    private CentralSettingsCacheService centralSettingsCacheService;
    @Reference
    private StaticContextService staticContextService;
    private final RestTemplate restTemplate;

    public FederatedSearchClientImpl() {
        this.restTemplate = new RestTemplate();
    }

    @Override
    public RecordsResponse getRecords(Integer start, Integer size, String params) throws Exception {
        return getRecords(null, start, size, params, null, null, null, null, null);
    }

    @Override
    public RecordsResponse getRecords(String sourceId, Integer start, Integer size, String params) throws Exception {
        return getRecords(sourceId, start, size, params, null, null, null, null, null);
    }

    @Override
    public RecordsResponse getRecords(String sourceId,
                                      Integer start,
                                      Integer size,
                                      String params,
                                      Integer facetSize,
                                      Boolean nativeQuery,
                                      String q,
                                      String c,
                                      String sort) throws Exception {
        long groupId = staticContextService.getThemeDisplay().getScopeGroupId();
        CentralSettingsDto centralSettingsDto = centralSettingsCacheService.getCentralSettings(groupId);
        ArenaSystemConfiguration arenaSystemConfiguration = ArenaUtil.getArenaSystemConfiguration();
        ArenaGroupConfiguration arenaGroupConfiguration = ArenaUtil.getArenaGroupConfiguration(groupId);

        String federatedSearchApiEndpoint = arenaSystemConfiguration.federatedSearchApiEndpoint();
        String customerId = arenaGroupConfiguration.federatedSearchCustomerAlias();

        sourceId = (sourceId != null) ? sourceId : centralSettingsDto.getDefaultSourceId();
        size = (size != 0) ? size : 10;
        facetSize = (facetSize != null) ? facetSize : 0;
        nativeQuery = (nativeQuery != null) ? nativeQuery : false;
        q = (q != null) ? q : "*";
        sort = (sort != null) ? sort : String.format("\"field\":\"%s\",\"order\":\"desc\"", SCORE_SORT_FIELD);

        SearchCriteriaDto searchCriteria = SearchCriteriaDto.builder()
                .sourceId(sourceId)
                .size(size)
                .start(start)
                .facetSize(facetSize)
                .nativeQuery(nativeQuery)
                .q(q)
                .c(c)
                .sort(sort)
                .build();

        String url = buildUrl(federatedSearchApiEndpoint, customerId, searchCriteria, params);
        return RecordsResponse.builder()
                .list(getFedEventResponses(url))
                .build();
    }

    private String buildUrl(String endpoint, String customerId, SearchCriteriaDto criteria, String params) {
        StringBuilder urlBuilder = new StringBuilder();
        urlBuilder.append(endpoint)
                .append("/customers/")
                .append(customerId)
                .append("/search")
                .append(criteria.toQueryString());

        if (params != null && !params.isBlank()) {
            urlBuilder.append("&").append(params);
        }
        return urlBuilder.toString();
    }

    private List<FedEventResponse> getFedEventResponses(String url) {
        List<FedEventResponse> fedEventResponses = new ArrayList<>();
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.TEXT_EVENT_STREAM));
            HttpEntity<String> entity = new HttpEntity<>(headers);

            ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);

            if (!response.getStatusCode().is2xxSuccessful()) {
                log.error("Failed to get response from URL: " + url + ". Status code: " + response.getStatusCode());
                return fedEventResponses;
            }

            String body = response.getBody();
            if (body == null || body.isBlank()) {
                log.warn("Response body is empty for URL: " + url);
                return fedEventResponses;
            }

            Arrays.stream(body.split(EVENT_SEPARATOR))
                    .filter(event -> event.contains(SEARCH_EVENT))
                    .map(this::extractDataFromEvent)
                    .filter(Objects::nonNull)
                    .forEach(data -> fedEventResponses.add(new FedEventResponse(data)));

        } catch (Exception e) {
            log.error("Exception occurred while fetching FedEventResponses from URL: " + url, e);
        }
        return fedEventResponses;
    }

    private String extractDataFromEvent(String event) {
        int dataIndex = event.indexOf(DATA_PREFIX);
        if (dataIndex != -1) {
            return event.substring(dataIndex + DATA_PREFIX.length()).trim();
        }
        return null;
    }

    @Override
    public RecordsResponse getRecordsByLMSSearchIds(String query,
                                                    String type,
                                                    Integer start,
                                                    Integer size) throws Exception {
        LMSSearchResultResponse lmsSearchResultResponse = arenaLocalServiceClient.getLMSSearchResult(query, type, start, size);
        String recordIds = lmsSearchResultResponse.getLmsList()
                .stream()
                .map(LMSSearchResponse::getRecordId)
                .map(id -> "\"" + id + "\"")
                .collect(Collectors.joining(","));
        String c = String.format("\"field\":\"%s\",\"values\":[%s]", ALMA_ID_FIELD, recordIds);

        RecordsResponse recordsResponse = getRecords(null, start, size, null, null, null, null, c, null);
        recordsResponse.setTotal(lmsSearchResultResponse.getTotal());
        return recordsResponse;
    }

}