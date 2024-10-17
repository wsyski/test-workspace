<%@ page import="com.liferay.asset.kernel.model.AssetEntry" %>
<%@ page import="com.liferay.portal.kernel.util.HashMapBuilder" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Map" %>
<%@ include file="init.jsp" %>
<%@ taglib uri="http://liferay.com/tld/aui" prefix="aui" %>
<%@ taglib uri="http://liferay.com/tld/ddm" prefix="liferay-ddm" %>
<%@ taglib uri="http://liferay.com/tld/portlet" prefix="liferay-portlet" %>
<%@ taglib uri="http://liferay.com/tld/theme" prefix="liferay-theme" %>
<%@ taglib uri="http://liferay.com/tld/social-bookmarks" prefix="liferay-social-bookmarks" %>

<%
    List<AssetEntry> entries = displayContext.getAssetEntries();
    Map<String, Object> contextObjects = HashMapBuilder.<String, Object>put(
            "socialBookmarksDisplayStyle", linkedAssetListPortletInstanceConfiguration.socialBookmarksDisplayStyle()

    ).put(
            "socialBookmarksTypes", String.join(",", linkedAssetListPortletInstanceConfiguration.socialBookmarksTypes())

    ).build();
%>


<liferay-ddm:template-renderer
        className="<%= AssetEntry.class.getName() %>"
        displayStyle="<%= linkedAssetListPortletInstanceConfiguration.displayStyle() %>"
        displayStyleGroupId="<%= displayContext.getDisplayStyleGroupId() %>"
        entries="<%= entries %>"
        contextObjects="<%= contextObjects %>"
>
</liferay-ddm:template-renderer>
