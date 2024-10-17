package com.axiell.arena.liferay.modules.overrides.k10_search_portlet.portlet.action;

import com.axiell.arena.liferay.modules.overrides.k10_search_portlet.constants.K10SearchPortletKeys;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Portlet;
import com.liferay.portal.kernel.portlet.ConfigurationAction;
import com.liferay.portal.kernel.portlet.DefaultConfigurationAction;
import com.liferay.portal.kernel.portlet.PortletConfigFactoryUtil;
import com.liferay.portal.kernel.service.PortletLocalServiceUtil;
import com.liferay.portal.kernel.settings.ParameterMapSettings;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.WebKeys;
import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Modified;
import org.osgi.service.component.annotations.Reference;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletConfig;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Component(
        immediate = true,
        property = {
                "javax.portlet.name=" + K10SearchPortletKeys.K10_SEARCH_PORTLET_NAME,
                "service.ranking:Integer=100"
        },
        service = ConfigurationAction.class
)
public class K10SearchPortletConfigurationAction extends DefaultConfigurationAction {
    private static final Log _log = LogFactoryUtil.getLog(K10SearchPortletConfigurationAction.class);

    @Override
    public String getJspPath(HttpServletRequest httpServletRequest) {
        return "/configuration.jsp";
    }

    @Override
    public void processAction(final PortletConfig portletConfig, final ActionRequest actionRequest, final ActionResponse actionResponse) throws Exception {
        String collectionsKey = ParameterMapSettings.PREFERENCES_PREFIX  + K10SearchPortletKeys.KEY_COLLECTION + K10SearchPortletKeys.PREFERENCES_POSTFIX;
        String[] collections = ParamUtil.getStringValues(actionRequest, collectionsKey);
        setPreference(actionRequest, K10SearchPortletKeys.KEY_COLLECTION, collections);
        super.processAction(portletConfig,actionRequest,actionResponse);
    }
    @Override
    protected PortletConfig getSelPortletConfig(final HttpServletRequest httpServletRequest) {
        ThemeDisplay themeDisplay = (ThemeDisplay)httpServletRequest.getAttribute(WebKeys.THEME_DISPLAY);
        String portletResource = ParamUtil.getString(httpServletRequest, "portletResource");
        Portlet selPortlet = PortletLocalServiceUtil.getPortletById(themeDisplay.getCompanyId(), portletResource);
        // ServletContext servletContext = (ServletContext)httpServletRequest.getAttribute(WebKeys.CTX);
        return PortletConfigFactoryUtil.create(selPortlet, servletContext);
    }

    @Modified
    @Activate
    protected void activate(final BundleContext bundleContext, final Map<String, Object> properties) {
        _log.info(" activate: " + K10SearchPortletConfigurationAction.class.getName());
    }

    @Reference(
            target = "(osgi.web.symbolicname=" + K10SearchPortletKeys.K10_SEARCH_PORTLET_BUNDLE_NAME + ")",
            unbind = "-"
    )
    private ServletContext servletContext;
}
