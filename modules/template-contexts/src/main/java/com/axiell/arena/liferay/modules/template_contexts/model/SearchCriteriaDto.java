package com.axiell.arena.liferay.modules.template_contexts.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

@AllArgsConstructor
@Builder
@Value
public class SearchCriteriaDto {

    String sourceId;
    String q;
    String queryString;
    List<String> facetField;
    List<String> c;
    List<String> fc;
    String termFilter;
    List<String> sort;
    Integer start;
    Integer size;
    Integer facetSize;

    public String toQueryString() {
        Map<String, Object> queryParams = new LinkedHashMap<>();
        queryParams.put("sourceId", sourceId);
        queryParams.put("size", size);
        queryParams.put("start", start);
        queryParams.put("facetSize", facetSize);
        queryParams.put("q", q);
        queryParams.put("c", c);
        queryParams.put("fc", fc);
        queryParams.put("facetField", facetField);
        queryParams.put("termFilter", termFilter);
        queryParams.put("sort", sort);

        String query = queryParams.entrySet().stream()
                .filter(entry -> entry.getValue() != null)
                .flatMap(entry -> toKeyValuePair(entry.getKey(), entry.getValue()))
                .collect(Collectors.joining("&", "?", ""));

        return query.equals("?") ? "" : query;
    }

    private Stream<String> toKeyValuePair(String key, Object value) {
        if (value instanceof Iterable<?>) {
            Iterable<?> iterable = (Iterable<?>) value;
            return StreamSupport.stream(iterable.spliterator(), false)
                    .map(item -> {
                        String s = String.valueOf(item);
                        return key + "=" + s;
                    });
        } else {
            return Stream.of(key + "=" + value);
        }
    }
}
