<#if response??>
    <ul>
        <#if response.list?has_content>
            <#list response.list as res>

                <h1>Search Results</h1>
                <#assign json = res.json?eval>

                <p>Source ID: ${json.sourceId}</p>
                <p>Source Type: ${json.sourceType}</p>
                <p>Start: ${json.start}</p>
                <p>Size: ${json.size}</p>
                <p>Facet Size: ${json.facetSize}</p>
                <p>Total Hits: ${json.totalHits}</p>

                <#if json.hits?has_content>
                    <ul>
                        <#list json.hits as hit>
                            <li>
                                <p>Entity Type: ${hit.entityType}</p>

                                <#if hit.entity??>
                                    <#if hit.entity.name??>
                                        <p>Name: ${hit.entity.name}</p>
                                    </#if>

                                    <#if hit.entity.description?has_content>
                                        <p>Description: ${hit.entity.description[0]}</p>
                                    </#if>

                                    <#if hit.entity.id??>
                                        <p>ID: ${hit.entity.id}</p>
                                    </#if>

                                    <#if hit.entity.idSort??>
                                        <p>ID Sort: ${hit.entity.idSort}</p>
                                    </#if>
                                    <#if hit.entity.custom??>
                                        <#if hit.entity.custom.inscriptionContent?has_content>
                                            <p>Inscription Content:</p>
                                            <ul>
                                                <#list hit.entity.custom.inscriptionContent.value as item>
                                                    <#if item.value?has_content>
                                                        <li>${item.value?html}</li>
                                                    </#if>
                                                </#list>
                                            </ul>
                                        </#if>

                                        <#if hit.entity.custom.itemStatus?has_content>
                                            <p>Item Status:</p>
                                            <p>${hit.entity.custom.itemStatus.value[0].value?html}</p>
                                        </#if>

                                        <#if hit.entity.custom.material?has_content>
                                            <p>Material:</p>
                                            <ul>
                                                <#list hit.entity.custom.material.value as item>
                                                    <#if item.value?has_content>
                                                        <li>${item.value?html}</li>
                                                    </#if>
                                                </#list>
                                            </ul>
                                        </#if>
                                    </#if>
                                    <#if hit.entity.image?has_content>
                                        <p>Images:</p>
                                        <ul>
                                            <#list hit.entity.image as img>
                                                <#if img?has_content>
                                                    <li><a href="${img.url}" target="_blank">${img.url}</a></li>
                                                </#if>
                                            </#list>
                                        </ul>
                                    </#if>

                                    <#if hit.entity.url?has_content>
                                        <p>URLs:</p>
                                        <ul>
                                            <#list hit.entity.url as link>
                                                <#if link?has_content>
                                                    <li><a href="${link.url}" target="_blank">${link.url}</a></li>
                                                </#if>
                                            </#list>
                                        </ul>
                                    </#if>

                                    <#if hit.entity.audience?has_content>
                                        <p>Audience: ${hit.entity.audience[0]}</p>
                                    </#if>

                                    <#if hit.entity.category?has_content>
                                        <p>Category: ${hit.entity.category[0]}</p>
                                    </#if>

                                    <#if hit.entity.classificationCode?has_content>
                                        <p>Classification Code: ${hit.entity.classificationCode[0]}</p>
                                    </#if>

                                    <#if hit.entity.contributor??>
                                        <p>Contributors:</p>
                                        <ul>
                                            <#if hit.entity.contributor.author?has_content>
                                                <li><strong>Author:</strong> ${hit.entity.contributor.author[0].value}
                                                </li>
                                            </#if>
                                            <#if hit.entity.contributor.artist?has_content>
                                                <li><strong>Artist:</strong> ${hit.entity.contributor.artist[0].value}
                                                </li>
                                            </#if>
                                            <#if hit.entity.contributor.illustrator?has_content>
                                                <li>
                                                    <strong>Illustrator:</strong> ${hit.entity.contributor.illustrator[0].value}
                                                </li>
                                            </#if>
                                            <#if hit.entity.contributor.translator?has_content>
                                                <li>
                                                    <strong>Translator:</strong> ${hit.entity.contributor.translator[0].value}
                                                </li>
                                            </#if>
                                        </ul>
                                    </#if>

                                    <#if hit.entity.genre?has_content>
                                        <p>Genre: ${hit.entity.genre[0]}</p>
                                    </#if>

                                    <#if hit.entity.identifier??>
                                        <#if hit.entity.identifier.isbn?has_content>
                                            <p>ISBN: ${hit.entity.identifier.isbn[0]}</p>
                                        </#if>
                                        <#if hit.entity.identifier.ean?has_content>
                                            <p>EAN: ${hit.entity.identifier.ean[0]}</p>
                                        </#if>
                                    </#if>

                                    <#if hit.entity.language?has_content>
                                        <p>Language: ${hit.entity.language[0].value}</p>
                                    </#if>

                                    <#if hit.entity.publicationYear?has_content>
                                        <p>Publication Year: ${hit.entity.publicationYear[0]}</p>
                                    </#if>

                                    <#if hit.entity.publisher?has_content>
                                        <p>Publisher: ${hit.entity.publisher[0]}</p>
                                    </#if>

                                    <#if hit.entity.resourceType?has_content>
                                        <p>Resource Type: ${hit.entity.resourceType[0]}</p>
                                    </#if>

                                    <#if hit.entity.subject?has_content>
                                        <p>Subject: ${hit.entity.subject[0]}</p>
                                    </#if>

                                    <#if hit.entity.mediaClass??>
                                        <p>Media Class: ${hit.entity.mediaClass}</p>
                                    </#if>

                                    <#if hit.entity.mediaClassGroup??>
                                        <p>Media Class Group: ${hit.entity.mediaClassGroup}</p>
                                    </#if>

                                    <#if hit.entity.mediaClassIcon??>
                                        <p>Media Class Icon: ${hit.entity.mediaClassIcon}</p>
                                    </#if>

                                    <#if hit.entity.almaId??>
                                        <p>Alma ID: ${hit.entity.almaId}</p>
                                    </#if>

                                    <#if hit.entity.agencyId??>
                                        <p>Agency ID: ${hit.entity.agencyId}</p>
                                    </#if>
                                </#if>
                            </li>
                        </#list>
                    </ul>
                </#if>


            </#list>
        </#if>

    </ul>
</#if>