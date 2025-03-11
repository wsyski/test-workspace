<#if jsonTestCase??>
    <#assign testCaseMap = jsonTestCase?eval>
    <#assign sourceId = testCaseMap.sourceId>
    <#assign start = testCaseMap.start>
    <#assign size = testCaseMap.size>
    <#assign facetSize = testCaseMap.facetSize>
    <#assign nativeQuery = testCaseMap.nativeQuery>
    <#assign q = testCaseMap.q>
    <#assign c = testCaseMap.c![]>
    <#assign fc = testCaseMap.fc![]>
    <#assign facetField = testCaseMap.facetField![]>
    <#assign sort = testCaseMap.sort![]>

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
