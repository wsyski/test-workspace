package com.axiell.arena.liferay.modules.overrides.dynamic_include_top_js;

import com.axiell.arena.liferay.modules.arena.configuration.ArenaSystemConfiguration;
import com.axiell.arena.liferay.modules.arena.util.ArenaUtil;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONSerializer;
import org.apache.commons.lang3.StringUtils;
import org.osgi.service.component.annotations.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

@Component(
        immediate = true,
        service = com.liferay.portal.kernel.servlet.taglib.DynamicInclude.class
)
public class DynamicIncludeTopJs implements com.liferay.portal.kernel.servlet.taglib.DynamicInclude {

    private static final String LIFERAY_SHARED_AGENCY_USER = "LIFERAY_SHARED_AGENCY_USER";
    private static final String KEY_HTML_COMMON_THEMES_TOP_JS_JSPF_RESOURCES = "/html/common/themes/top_js.jspf#resources";

    private static String getPatronDTOAsJSON(final Object agencyUser) {
        PatronDTO patronDTO = PatronDTOFactory.get(agencyUser);
        JSONSerializer jsonSerializer = JSONFactoryUtil.createJSONSerializer();
        return jsonSerializer.serializeDeep(patronDTO);
    }

    @Override
    public void include(final HttpServletRequest request, final HttpServletResponse response, final String key) throws IOException {
        HttpSession session = request.getSession(true);
        Object agencyUser = session.getAttribute(LIFERAY_SHARED_AGENCY_USER);
        ArenaSystemConfiguration arenaSystemConfiguration = ArenaUtil.getArenaSystemConfiguration();
        String localApiEndpoint = arenaSystemConfiguration.localApiEndpoint();
        String federatedSearchApiEndpoint = arenaSystemConfiguration.federatedSearchApiEndpoint();
        String transactionApiEndpoint = arenaSystemConfiguration.transactionApiEndpoint();
        int maxInactiveInterval = session.getMaxInactiveInterval();
        String patronDTOAsJSON = getPatronDTOAsJSON(agencyUser);
        PrintWriter printWriter = response.getWriter();
        String varPatronValue = patronDTOAsJSON == null ? StringUtils.EMPTY : "=" + patronDTOAsJSON;
        String js = String.join("\n",
                "<script type=\"text/javascript\">",
                "(function (exports) {",
                "    'use strict';",
                "    var patron" + varPatronValue + ";",
                "    var maxInactiveInterval = " + maxInactiveInterval + ";",
                "    var localApiEndpoint = \"" + localApiEndpoint + "\";",
                "    var federatedSearchApiEndpoint = \"" + federatedSearchApiEndpoint + "\";",
                "    var transactionApiEndpoint = \"" + transactionApiEndpoint + "\";",
                "    function isSignedIn() {",
                "        return !!patron;",
                "    }",
                "    function getMaxInactiveInterval() {",
                "        return maxInactiveInterval;",
                "    }",
                "    function getApiEndpoint(localhostApiEndpoint, proxyPathname) {",
                "        var localhostPathname = (new URL(localhostApiEndpoint)).pathname;",
                "        if (localhostPathname.endsWith('/')) {",
                "           localhostPathname = localhostPathname.slice(0, -1);",
                "        }",
                "        var localhostPort = (new URL(localhostApiEndpoint)).port;",
                "        var localhostProtocol = (new URL(localhostApiEndpoint)).protocol;",
                "        var port = location.port;",
                "        var hostname = location.hostname;",
                "        var isLocalhostApiEndpoint = localhostProtocol === 'https' || hostname === 'localhost'",
                "        switch (port) {",
                "        case undefined:",
                "        case '':",
                "        case '80':",
                "        case '443':",
                "            return proxyPathname;",
                "        case '3000':",
                "        case '6080':",
                "        case '8080':",
                "        case '16519':",
                "            return isLocalhostApiEndpoint ? localhostApiEndpoint : '//' + hostname + ':' + localhostPort + localhostPathname;",
                "        default:",
                "            return localhostApiEndpoint;",
                "        }",
                "    }",
                "    function getLocalApiEndpoint() {",
                "        return getApiEndpoint(localApiEndpoint, '/local-rest/api/v1');",
                "    }",
                "    function getFederatedSearchApiEndpoint() {",
                "        return getApiEndpoint(federatedSearchApiEndpoint, '/federated-search');",
                "    }",
                "    function getTransactionApiEndpoint() {",
                "        return getApiEndpoint(transactionApiEndpoint, '/transaction');",
                "    }",
                "    function getPatron() {",
                "        return patron;",
                "    }",
                "    function setPatron(p) {",
                "        patron = p;",
                "    }",
                "    exports.isSignedIn = isSignedIn;",
                "    exports.getMaxInactiveInterval = getMaxInactiveInterval;",
                "    exports.getLocalApiEndpoint = getLocalApiEndpoint;",
                "    exports.getFederatedSearchApiEndpoint = getFederatedSearchApiEndpoint;",
                "    exports.getTransactionApiEndpoint = getTransactionApiEndpoint;",
                "    exports.getPatron = getPatron;",
                "    exports.setPatron = setPatron;",
                " } (window.Arena = window.Arena || {}));",
                "</script>");
        printWriter.println(js);
    }

    @Override
    public void register(DynamicIncludeRegistry dynamicIncludeRegistry) {
        dynamicIncludeRegistry.register(KEY_HTML_COMMON_THEMES_TOP_JS_JSPF_RESOURCES);
    }
}
