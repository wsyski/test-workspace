<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet" %>
<%@ taglib uri="http://liferay.com/tld/aui" prefix="aui" %>
<%@ taglib uri="http://liferay.com/tld/frontend" prefix="liferay-frontend" %>
<%@ taglib uri="http://liferay.com/tld/portlet" prefix="liferay-portlet" %>
<%@ taglib uri="http://liferay.com/tld/theme" prefix="liferay-theme" %>
<%@ taglib uri="http://liferay.com/tld/ui" prefix="liferay-ui" %>

<%@ page import="com.axiell.arena.liferay.modules.overrides.k10_search_portlet.K10SearchPortletDisplayContext" %>
<%@ page import="com.axiell.arena.liferay.modules.overrides.k10_search_portlet.ViewMode" %>
<%@ page import="com.axiell.arena.liferay.modules.overrides.k10_search_portlet.constants.K10SearchPortletKeys" %>
<%@ page import="com.liferay.portal.kernel.settings.ParameterMapSettings" %>
<%@ page import="com.liferay.portal.kernel.util.Constants" %>
<%@ page import="com.liferay.portal.kernel.util.HtmlUtil" %>
<%@ page import="java.util.Enumeration" %>
<%@ page import="javax.portlet.ValidatorException" %>

<liferay-theme:defineObjects/>

<portlet:defineObjects/>

<%
    K10SearchPortletDisplayContext displayContext = new K10SearchPortletDisplayContext(renderRequest, portletPreferences);
%>

<liferay-portlet:actionURL portletConfiguration="<%= true %>" var="configurationActionURL"/>
<liferay-portlet:renderURL portletConfiguration="<%= true %>" var="configurationRenderURL"/>

<liferay-frontend:edit-form
        action="<%= configurationActionURL %>"
        method="post"
        name="fm"
>
    <aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= Constants.UPDATE %>"/>
    <aui:input name="redirect" type="hidden" value="<%= configurationRenderURL %>"/>

    <liferay-ui:error exception="<%= ValidatorException.class %>">
        <%
            ValidatorException ve = (ValidatorException) errorException;
        %>

        <liferay-ui:message key="invalidConfigurationValues"/>

        <%
            Enumeration enu = ve.getFailedKeys();
            while (enu.hasMoreElements()) {
                String key = (String) enu.nextElement();
        %>
        <strong><%= HtmlUtil.escape(key) %>
        </strong><%= (enu.hasMoreElements()) ? ", " : "." %>
        <% } %>
    </liferay-ui:error>

    <liferay-frontend:edit-form-body>
        <liferay-frontend:fieldset
                collapsible="<%= true %>"
                label="display-settings"
        >
            <aui:field-wrapper>
                <aui:input name="<%=ParameterMapSettings.PREFERENCES_PREFIX+K10SearchPortletKeys.KEY_VIEW_MODE+K10SearchPortletKeys.PREFERENCES_POSTFIX%>" type="radio"
                           label="<%=K10SearchPortletKeys.KEY_VIEW_MODE  +'.'+ViewMode.FULL.name()+K10SearchPortletKeys.LABEL_POSTFIX%>"
                           title="<%=K10SearchPortletKeys.KEY_VIEW_MODE  +'.'+ViewMode.FULL.name()+K10SearchPortletKeys.TITLE_POSTFIX%>"
                           value="<%=ViewMode.FULL%>" checked="<%= displayContext.getViewMode() == ViewMode.FULL%>"/>
                <aui:input name="<%=ParameterMapSettings.PREFERENCES_PREFIX+K10SearchPortletKeys.KEY_VIEW_MODE+K10SearchPortletKeys.PREFERENCES_POSTFIX%>" type="radio"
                           label="<%=K10SearchPortletKeys.KEY_VIEW_MODE  +'.'+ViewMode.BRIEF.name()+K10SearchPortletKeys.LABEL_POSTFIX%>"
                           title="<%=K10SearchPortletKeys.KEY_VIEW_MODE  +'.'+ViewMode.BRIEF.name()+K10SearchPortletKeys.TITLE_POSTFIX%>"
                           value="<%=ViewMode.BRIEF%>" checked="<%= displayContext.getViewMode() == ViewMode.BRIEF%>"/>
            </aui:field-wrapper>
            <aui:field-wrapper>
                <aui:input name="<%=ParameterMapSettings.PREFERENCES_PREFIX+K10SearchPortletKeys.KEY_BASE_URL+K10SearchPortletKeys.PREFERENCES_POSTFIX%>"
                           label="<%=K10SearchPortletKeys.KEY_BASE_URL+K10SearchPortletKeys.LABEL_POSTFIX%>"
                           title="<%=K10SearchPortletKeys.KEY_BASE_URL+K10SearchPortletKeys.TITLE_POSTFIX%>"
                           value="<%= displayContext.getBaseUrl() %>"></aui:input>
            </aui:field-wrapper>
            <aui:field-wrapper>
                <aui:input name="<%=ParameterMapSettings.PREFERENCES_PREFIX+K10SearchPortletKeys.KEY_K10_SEARCH_PAGE+K10SearchPortletKeys.PREFERENCES_POSTFIX%>"
                           label="<%=K10SearchPortletKeys.KEY_K10_SEARCH_PAGE+K10SearchPortletKeys.LABEL_POSTFIX%>"
                           title="<%=K10SearchPortletKeys.KEY_K10_SEARCH_PAGE+K10SearchPortletKeys.TITLE_POSTFIX%>"
                           value="<%= displayContext.getSearchPage() %>"></aui:input>
            </aui:field-wrapper>
            <aui:field-wrapper>
                <aui:input name="<%=ParameterMapSettings.PREFERENCES_PREFIX+K10SearchPortletKeys.KEY_SEARCH_QUERY_PARAMETER+K10SearchPortletKeys.PREFERENCES_POSTFIX%>"
                           label="<%=K10SearchPortletKeys.KEY_SEARCH_QUERY_PARAMETER+K10SearchPortletKeys.LABEL_POSTFIX%>"
                           title="<%=K10SearchPortletKeys.KEY_SEARCH_QUERY_PARAMETER+K10SearchPortletKeys.TITLE_POSTFIX%>"
                           value="<%= displayContext.getSearchQueryParameter() %>"></aui:input>
            </aui:field-wrapper>
            <aui:field-wrapper>
                <aui:select name="<%=ParameterMapSettings.PREFERENCES_PREFIX+K10SearchPortletKeys.KEY_PAGE_SIZE+K10SearchPortletKeys.PREFERENCES_POSTFIX%>"
                            label="<%=K10SearchPortletKeys.KEY_PAGE_SIZE+K10SearchPortletKeys.LABEL_POSTFIX%>"
                            title="<%=K10SearchPortletKeys.KEY_PAGE_SIZE+K10SearchPortletKeys.TITLE_POSTFIX%>"
                            value="<%= HtmlUtil.escape(displayContext.getPageSize()) %>"
                            required="true">
                    <%
                        for (int value : K10SearchPortletKeys.PAGE_SIZES) {
                    %>
                    <aui:option label="<%=value %>" value="<%=value %>"
                                selected="<%=displayContext.getPageSize() != null && Integer.parseInt(displayContext.getPageSize()) == value %>"/>
                    <% } %>
                </aui:select>
            </aui:field-wrapper>
        </liferay-frontend:fieldset>
        <liferay-frontend:fieldset
                collapsible="<%= true %>"
                label="collection-settings">
            <%
                for (String collection : K10SearchPortletKeys.COLLECTIONS) {
            %>
            <aui:field-wrapper>
                <label>
                    <input class="field" name="<%=displayContext.getInputName(K10SearchPortletKeys.KEY_COLLECTION)%>" type="checkbox"
                           value="<%=HtmlUtil.escape(collection) %>"
                           title="<%=collection %>" <%=displayContext.getCollectionChecked(collection) %>/>
                    <liferay-ui:message key="<%=K10SearchPortletKeys.KEY_COLLECTION+'.'+collection+K10SearchPortletKeys.LABEL_POSTFIX%>"/>
                </label>
            </aui:field-wrapper>
            <% } %>
        </liferay-frontend:fieldset>
    </liferay-frontend:edit-form-body>

    <liferay-frontend:edit-form-footer>
        <aui:button type="submit"/>
        <aui:button type="cancel"/>
    </liferay-frontend:edit-form-footer>
</liferay-frontend:edit-form>
