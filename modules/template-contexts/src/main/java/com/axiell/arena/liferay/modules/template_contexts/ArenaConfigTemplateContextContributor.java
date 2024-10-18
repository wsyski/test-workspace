package com.axiell.arena.liferay.modules.template_contexts;

import com.axiell.arena.liferay.modules.arena.configuration.ArenaGroupConfiguration;
import com.axiell.arena.liferay.modules.arena.configuration.ArenaSystemConfiguration;
import com.axiell.arena.liferay.modules.arena.exception.ArenaRuntimeException;
import com.axiell.arena.liferay.modules.common_services.configuration.CommonServicesGroupConfiguration;
import com.axiell.arena.liferay.modules.common_services.configuration.CommonServicesSystemConfiguration;
import com.liferay.portal.configuration.module.configuration.ConfigurationProviderUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.template.TemplateContextContributor;
import com.liferay.portal.kernel.util.PortalUtil;
import org.osgi.service.component.annotations.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;


/**
 * Provides the ability to inject variables into a template's context. You can
 * inject custom variables into your platform's global context (e.g., themes,
 * application display templates, DDM templates, etc.) or into the theming
 * context only.
 *
 * <p>
 * <pre>
 * <code>
 * property = {"type=" + TemplateContextContributor.TYPE_GLOBAL}
 * </code>
 * </pre>
 * </p>
 *
 * @author Ali Ahdab
 */
@Component(
        immediate = true,
        property = "type=" + TemplateContextContributor.TYPE_GLOBAL,
        service = TemplateContextContributor.class
)
public class ArenaConfigTemplateContextContributor
        implements TemplateContextContributor {

    private static Method getMethod(final Object object, final String methodName) {
        if (object == null) {
            return null;
        }
        try {
            return object.getClass().getMethod(methodName);
        } catch (NoSuchMethodException ex) {
            return null;
        }
    }

    private static Object getValue(final Object object, final String methodName) {
        Method method = getMethod(object, methodName);
        if (method == null) {
            return null;
        }
        Object value = null;
        try {
            value = method.invoke(object);
        } catch (IllegalAccessException | InvocationTargetException ex) {
            return null;
        }
        return value;
    }

    /**
     * @param contextObjects     the variables available in the context
     * @param httpServletRequest the HTTP servlet request
     */
    @Override
    public void prepare(
            Map<String, Object> contextObjects,
            HttpServletRequest httpServletRequest) {
        try {
            String LIFERAY_SHARED_AGENCY_USER = "LIFERAY_SHARED_AGENCY_USER";
            HttpSession session = httpServletRequest.getSession(true);
            Object agencyUser = session.getAttribute(LIFERAY_SHARED_AGENCY_USER);
            boolean isSignedIn = false;
            if (agencyUser != null) {
                Object arenaUserId = getValue(agencyUser, "getId");
                Object patronId = getValue(agencyUser, "getSelectedExternalUserId");
                isSignedIn = (patronId != null || arenaUserId != null);
            }
            long scopeGroupId = PortalUtil.getScopeGroupId(httpServletRequest);
            ArenaSystemConfiguration arenaSystemConfiguration =
                    ConfigurationProviderUtil.getSystemConfiguration(ArenaSystemConfiguration.class);
            ArenaGroupConfiguration arenaGroupConfiguration =
                    ConfigurationProviderUtil.getGroupConfiguration(ArenaGroupConfiguration.class, scopeGroupId);
            CommonServicesGroupConfiguration commonServicesGroupConfiguration =
                    ConfigurationProviderUtil.getGroupConfiguration(CommonServicesGroupConfiguration.class, scopeGroupId);
            CommonServicesSystemConfiguration commonServicesSystemConfiguration =
                    ConfigurationProviderUtil.getSystemConfiguration(CommonServicesSystemConfiguration.class);
            contextObjects.put("federatedSearchCustomerAlias", arenaGroupConfiguration.federatedSearchCustomerAlias());
            contextObjects.put("federatedSearchApiEndpoint", arenaSystemConfiguration.federatedSearchApiEndpoint());
            contextObjects.put("googleAnalyticsMeasurementId", commonServicesGroupConfiguration.googleAnalyticsMeasurementId());
            contextObjects.put("googleTagManagerId", commonServicesGroupConfiguration.googleTagManagerId());
            contextObjects.put("openingHoursCustomerId", commonServicesGroupConfiguration.openingHoursCustomerId());
            contextObjects.put("openingHoursApiEndpoint", commonServicesSystemConfiguration.openingHoursApiEndpoint());
            contextObjects.put("localApiEndpoint", arenaSystemConfiguration.localApiEndpoint());
            contextObjects.put("centralApiEndpoint", arenaSystemConfiguration.centralApiEndpoint());
            contextObjects.put("isSignedIn", isSignedIn);
        } catch (PortalException e) {
            throw new ArenaRuntimeException(e.getMessage(), e);
        }
    }

}
