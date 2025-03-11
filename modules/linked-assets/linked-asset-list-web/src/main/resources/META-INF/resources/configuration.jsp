<%@ taglib uri="http://liferay.com/tld/asset" prefix="liferay-asset" %>
<%@ page import="com.liferay.asset.kernel.model.AssetEntry" %>
<%@ page import="com.liferay.journal.model.JournalArticle" %>
<%@ page import="com.liferay.portal.kernel.util.Constants" %>

<%@ include file="init.jsp" %>
<%
    long classPK = 0;
%>

<liferay-portlet:actionURL portletConfiguration="<%= true %>" var="configurationActionURL" />

<liferay-portlet:renderURL portletConfiguration="<%= true %>" var="configurationRenderURL" />

<aui:form action="<%= configurationActionURL %>" method="post" name="fm">
    <aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= Constants.UPDATE %>" />
    <aui:input name="redirect" type="hidden" value="<%= configurationRenderURL %>" />
    <div class="portlet-configuration-body-content">
        <div class="container-fluid-1280">
            <div class="sheet">
                <div aria-multiselectable="true" class="panel-group panel-group-flush" role="tablist">
                    <aui:fieldset>
                        <div class="display-template">
                            <liferay-ddm:template-selector
                                    className="<%= AssetEntry.class.getName() %>"
                                    displayStyle="<%= linkedAssetListPortletInstanceConfiguration.displayStyle() %>"
                                    displayStyleGroupId="<%= displayContext.getDisplayStyleGroupId() %>"
                                    refreshURL="<%= configurationRenderURL %>"
                                    showEmptyOption="<%= true %>"
                            />
                        </div>

                        <label class="control-label">
                            <liferay-ui:message key="categories" />
                            <liferay-asset:asset-categories-selector
                                    className="<%= JournalArticle.class.getName() %>"
                                    classPK="<%= classPK %>"
                                    groupIds="<%= new long[] {displayContext.getDisplayStyleGroupId()} %>"
                                    categoryIds="<%= displayContext.getCategoryIdsAsString() %>" />
                        </label>

                    </aui:fieldset>
                </div>
            </div>
        </div>
    </div>

    <aui:button-row>
        <aui:button cssClass="btn-lg" type="submit" />
    </aui:button-row>
</aui:form>
