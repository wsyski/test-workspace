<#if testCase??>
    <#assign query = testCase.query>
    <#assign start = testCase.start>
    <#assign size = testCase.size>

    <#assign response = federatedSearchClient.getRecordsByAlmaRecordIds(query, start, size)>
    <#include "view.ftl">
</#if>
