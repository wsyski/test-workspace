<%@ page import="com.axiell.arena.liferay.modules.linked_assets.linked_asset_list.web.configuration.LinkedAssetListPortletInstanceConfiguration" %>
<%@ page import="com.axiell.arena.liferay.modules.linked_assets.linked_asset_list.web.portlet.LinkedAssetListDisplayContext" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet" %>
<%@ taglib uri="http://liferay.com/tld/aui" prefix="aui" %>
<%@ taglib uri="http://liferay.com/tld/ddm" prefix="liferay-ddm" %>
<%@ taglib uri="http://liferay.com/tld/portlet" prefix="liferay-portlet" %>
<%@ taglib uri="http://liferay.com/tld/theme" prefix="liferay-theme" %>
<%@ taglib uri="http://liferay.com/tld/aui" prefix="aui" %>
<%@ taglib uri="http://liferay.com/tld/portlet" prefix="liferay-portlet" %>
<%@ taglib uri="http://liferay.com/tld/theme" prefix="liferay-theme" %>
<%@ taglib uri="http://liferay.com/tld/ui" prefix="liferay-ui" %>

<liferay-theme:defineObjects />
<portlet:defineObjects />

<%
    LinkedAssetListDisplayContext displayContext = new LinkedAssetListDisplayContext(liferayPortletRequest);
    LinkedAssetListPortletInstanceConfiguration linkedAssetListPortletInstanceConfiguration = displayContext.getLinkedAssetPortletInstanceConfiguration();
%>
