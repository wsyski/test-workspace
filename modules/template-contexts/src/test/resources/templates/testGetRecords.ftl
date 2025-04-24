<#assign sourceId = "source123">
<#assign start = 10>
<#assign size = 5>
<#assign facetSize = 3>
<#assign q = "searchTerm">
<#assign c = ["c1", "c2"]>
<#assign fc = ["fc1"]>
<#assign facetField = ["field1", "field2"]>
<#assign sort = ["asc", "desc"]>

<#assign response = federatedSearchClient.getRecords(sourceId, start, size, facetSize, q, c, fc, facetField, sort)>
<#include "view.ftl">

