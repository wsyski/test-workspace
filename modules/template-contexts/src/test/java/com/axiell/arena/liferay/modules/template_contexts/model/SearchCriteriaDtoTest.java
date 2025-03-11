package com.axiell.arena.liferay.modules.template_contexts.model;

import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class SearchCriteriaDtoTest {

    @Test
    public void testToQueryString_AllFieldsSet() {
        SearchCriteriaDto dto = SearchCriteriaDto.builder()
                .sourceId("source123")
                .q("searchTerm")
                .queryString("customQuery")
                .facetField(List.of("field1", "field2"))
                .c(List.of("c1", "c2"))
                .fc(List.of("fc1"))
                .termFilter("filterTerm")
                .sort(List.of("sort1", "sort2"))
                .start(10)
                .size(20)
                .facetSize(5)
                .build();

        String expected = "?sourceId=source123&size=20&start=10&facetSize=5&q=searchTerm&c=c1&c=c2&fc=fc1&facetField=field1&facetField=field2&termFilter=filterTerm&sort=sort1&sort=sort2";
        assertEquals(expected, dto.toQueryString());
    }

    @Test
    public void testToQueryString_SomeFieldsSet() {
        SearchCriteriaDto dto = SearchCriteriaDto.builder()
                .sourceId("source123")
                .q("searchTerm")
                .start(0)
                .size(10)
                .build();

        String expected = "?sourceId=source123&size=10&start=0&q=searchTerm";
        assertEquals(expected, dto.toQueryString());
    }

    @Test
    public void testToQueryString_NoFieldsSet() {
        SearchCriteriaDto dto = SearchCriteriaDto.builder().build();
        String expected = "";
        assertEquals(expected, dto.toQueryString());
    }

    @Test
    public void testToQueryString_EmptyListFields() {
        SearchCriteriaDto dto = SearchCriteriaDto.builder()
                .sourceId("source123")
                .facetField(Collections.emptyList())
                .c(Collections.emptyList())
                .fc(Collections.emptyList())
                .sort(Collections.emptyList())
                .build();

        String expected = "?sourceId=source123";
        assertEquals(expected, dto.toQueryString());
    }

    @Test
    public void testToQueryString_NullFields() {
        SearchCriteriaDto dto = SearchCriteriaDto.builder()
                .sourceId(null)
                .q(null)
                .queryString(null)
                .facetField(null)
                .c(null)
                .fc(null)
                .termFilter(null)
                .sort(null)
                .start(null)
                .size(null)
                .facetSize(null)
                .build();

        String expected = "";
        assertEquals(expected, dto.toQueryString());
    }

    @Test
    public void testToQueryString_MixedFields() {
        SearchCriteriaDto dto = SearchCriteriaDto.builder()
                .sourceId("source123")
                .q("searchTerm")
                .facetField(List.of("field1"))
                .sort(List.of("asc"))
                .start(5)
                .size(15)
                .build();

        String expected = "?sourceId=source123&size=15&start=5&q=searchTerm&facetField=field1&sort=asc";
        assertEquals(expected, dto.toQueryString());
    }

    @Test
    public void testToQueryString_SingleListElement() {
        SearchCriteriaDto dto = SearchCriteriaDto.builder()
                .c(List.of("singleC"))
                .fc(List.of("singleFc"))
                .facetField(List.of("singleFacet"))
                .sort(List.of("singleSort"))
                .build();

        String expected = "?c=singleC&fc=singleFc&facetField=singleFacet&sort=singleSort";
        assertEquals(expected, dto.toQueryString());
    }

    @Test
    public void testToQueryString_SpecialCharacters() {
        SearchCriteriaDto dto = SearchCriteriaDto.builder()
                .q("search term with spaces")
                .termFilter("filter&value")
                .build();

        String expected = "?q=search term with spaces&termFilter=filter&value";
        assertEquals(expected, dto.toQueryString());
    }

    @Test
    public void testToQueryString_SizeAndStartOnly() {
        SearchCriteriaDto dto = SearchCriteriaDto.builder()
                .size(50)
                .start(100)
                .build();

        String expected = "?size=50&start=100";
        assertEquals(expected, dto.toQueryString());
    }
}
