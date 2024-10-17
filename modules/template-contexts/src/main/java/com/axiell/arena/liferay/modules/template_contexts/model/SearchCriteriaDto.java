package com.axiell.arena.liferay.modules.template_contexts.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.SneakyThrows;
import lombok.Value;

@AllArgsConstructor
@Builder
@Value
public class SearchCriteriaDto {

    String sourceId;
    Boolean nativeQuery;
    String q;
    String queryString;
    String facetField;
    String c;
    String fc;
    String termFilter;
    String sort;
    Integer start;
    Integer size;
    Integer facetSize;

    @SneakyThrows
    public String toQueryString() {
        StringBuilder sb = new StringBuilder("?");
        if (sourceId != null) sb.append("sourceId=").append(sourceId).append("&");
        if (size != null) sb.append("size=").append(size).append("&");
        if (start != null) sb.append("start=").append(start).append("&");
        if (facetSize != null) sb.append("facetSize=").append(facetSize).append("&");
        if (nativeQuery != null) sb.append("nativeQuery=").append(nativeQuery).append("&");
        if (q != null) sb.append("q=").append(q).append("&");
        if (c != null) sb.append("c=").append(c).append("&");
        if (sort != null) sb.append("sort=").append(sort).append("&");
        if (sb.charAt(sb.length() - 1) == '&') sb.deleteCharAt(sb.length() - 1);
        return sb.toString();
    }
}
