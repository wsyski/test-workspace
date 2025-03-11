<#if testCase??>
    <#assign query = testCase.query>
    <#assign start = testCase.start>
    <#assign size = testCase.size>
    <#assign type = testCase.type>

    <#assign response = federatedSearchClient.getRecordsByLMSSearchIds(query, type, start, size)>
    <#include "view.ftl">
</#if>
