<#if testCase??>
    <#assign sourceId = testCase.sourceId>
    <#assign start = testCase.start>
    <#assign size = testCase.size>
    <#assign facetSize = testCase.facetSize>
    <#assign nativeQuery = testCase.nativeQuery>
    <#assign q = testCase.q!''>
    <#assign c = testCase.c>
    <#assign fc = testCase.fc>
    <#assign facetField = testCase.facetField>
    <#assign sort = testCase.sort>

    <#assign response = federatedSearchClient.getRecords(
    sourceId,
    start,
    size,
    facetSize,
    nativeQuery,
    q,
    c,
    fc,
    facetField,
    sort
    )>

    <#include "view.ftl">
</#if>
