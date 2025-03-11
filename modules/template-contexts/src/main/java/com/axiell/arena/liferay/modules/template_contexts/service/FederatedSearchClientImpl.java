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
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.CustomLog;
import lombok.SneakyThrows;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
    @Reference
    private TemplateContextConfig templateContextConfig;

    @Override
    public RecordsResponse getRecords(String sourceId,
                                      Integer start,
                                      Integer size,
                                      Integer facetSize,
                                      String q,
                                      List<String> c,
                                      List<String> fc,
                                      List<String> facetField,
                                      List<String> sort) throws Exception {
        long groupId = staticContextService.getThemeDisplay().getScopeGroupId();
        CentralSettingsDto centralSettingsDto = centralSettingsCacheService.getCentralSettings(groupId);
        ArenaSystemConfiguration arenaSystemConfiguration = ArenaUtil.getArenaSystemConfiguration();
        ArenaGroupConfiguration arenaGroupConfiguration = ArenaUtil.getArenaGroupConfiguration(groupId);

        String federatedSearchApiEndpoint = arenaSystemConfiguration.federatedSearchApiEndpoint();
        String customerId = arenaGroupConfiguration.federatedSearchCustomerAlias();

        sourceId = (sourceId != null) ? sourceId : centralSettingsDto.getDefaultSourceId();
        size = (size != 0) ? size : 10;
        facetSize = (facetSize != null) ? facetSize : 0;
        q = (q != null) ? q : "*";
        sort = !CollectionUtils.isEmpty(sort) ? sort : List.of(String.format("\"field\":\"%s\",\"order\":\"desc\"", SCORE_SORT_FIELD));

        SearchCriteriaDto searchCriteria = SearchCriteriaDto.builder()
                .sourceId(sourceId)
                .size(size)
                .start(start)
                .facetSize(facetSize)
                .q(q)
                .c(c)
                .facetField(facetField)
                .fc(fc)
                .sort(sort)
                .build();

        String url = buildUrl(federatedSearchApiEndpoint, customerId, searchCriteria);
        log.debug("Constructed URL: " + url);
        return RecordsResponse.builder()
                .list(getFedEventResponses(url))
                .build();
    }

    @Override
    public RecordsResponse getRecords(
            String sourceId,
            Integer start,
            Integer size,
            Integer facetSize,
            String q,
            String c,
            String fc,
            String facetField,
            String sort) throws Exception {
        List<String> cList = convertToList(c);
        List<String> fcList = convertToList(fc);
        List<String> facetFieldList = convertToList(facetField);
        List<String> sortList = convertToList(sort);

        return getRecords(sourceId, start, size, facetSize, q, cList, fcList, facetFieldList, sortList);
    }

    private List<String> convertToList(String value) {
        if (value == null || value.trim().isEmpty()) {
            return Collections.emptyList();
        }
        return Collections.singletonList(value);
    }

    private String buildUrl(String endpoint, String customerId, SearchCriteriaDto criteria) {
        return endpoint +
                "/customers/" +
                customerId +
                "/search" +
                criteria.toQueryString();
    }

    private List<FedEventResponse> getFedEventResponses(String url) {
        List<FedEventResponse> fedEventResponses = new ArrayList<>();
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.TEXT_EVENT_STREAM));
            HttpEntity<String> entity = new HttpEntity<>(headers);

            ResponseEntity<String> response = templateContextConfig.restTemplate().exchange(url, HttpMethod.GET, entity, String.class);

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
        List<String> listIds = lmsSearchResultResponse.getLmsList()
                .stream()
                .map(LMSSearchResponse::getRecordId)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());

        return fetchAndProcessRecords(listIds, start, size, lmsSearchResultResponse.getTotal());
    }

    @Override
    public RecordsResponse getRecordsByAlmaRecordIds(String query,
                                                     Integer start,
                                                     Integer size) {
        List<String> listIds = Arrays.stream(query.split(","))
                .map(String::trim)
                .filter(id -> !id.isEmpty())
                .collect(Collectors.toList());

        return fetchAndProcessRecords(listIds, start, size, listIds.size());
    }

    @SneakyThrows
    private RecordsResponse fetchAndProcessRecords(List<String> listIds, Integer start, Integer size, int total) {
        if (listIds.isEmpty()) {
            log.warn("No Alma IDs provided to fetch records.");
            return new RecordsResponse(Collections.emptyList(), 0);
        }

        String recordIds = listIds.stream()
                .map(id -> "\"" + id + "\"")
                .collect(Collectors.joining(","));
        String c = String.format("\"field\":\"%s\",\"values\":[%s]", ALMA_ID_FIELD, recordIds);

        RecordsResponse recordsResponse = getRecords(start, size, c);

        List<FedEventResponse> sortedList = recordsResponse.getList().stream()
                .map(eventResponse -> FedEventResponse.builder()
                        .json(sortHitsByAlmaIdOrder(eventResponse.getJson(), listIds))
                        .build())
                .collect(Collectors.toList());

        recordsResponse.setTotal(total);
        recordsResponse.setList(sortedList);
        return recordsResponse;
    }

    @SneakyThrows
    public String sortHitsByAlmaIdOrder(String jsonInput, List<String> orderedAlmaIds) {
        ObjectMapper mapper = templateContextConfig.objectMapper();
        JsonNode rootNode = mapper.readTree(jsonInput);
        JsonNode hitsNode = rootNode.path("hits");

        if (!hitsNode.isArray()) {
            log.warn("The 'hits' node is not an array. Returning original JSON.");
            return jsonInput;
        }

        Map<String, JsonNode> almaIdToHitMap = new HashMap<>();
        for (JsonNode hit : hitsNode) {
            JsonNode almaIdNode = hit.path("entity").path(ALMA_ID_FIELD);
            if (!almaIdNode.isMissingNode() && almaIdNode.isTextual()) {
                String almaId = almaIdNode.asText();
                almaIdToHitMap.put(almaId, hit);
            } else {
                log.warn("Hit does not contain a valid Alma ID: " + hit);
            }
        }

        ArrayNode sortedHitsArray = mapper.createArrayNode();
        for (String almaId : orderedAlmaIds) {
            JsonNode hit = almaIdToHitMap.get(almaId);
            if (hit != null) {
                sortedHitsArray.add(hit);
            } else {
                log.error("Warning: Alma ID '" + almaId + "' not found in the hits array.");
            }
        }

        if (rootNode instanceof ObjectNode) {
            ((ObjectNode) rootNode).set("hits", sortedHitsArray);
            return mapper.writeValueAsString(rootNode);
        } else {
            log.warn("Root node is not an ObjectNode. Returning original JSON.");
            return jsonInput;
        }
    }

    private RecordsResponse getRecords(Integer start, Integer size, String c) throws Exception {
        return getRecords(null, start, size, null, null, c, null, null, null);
    }

}
