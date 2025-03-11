package com.axiell.arena.liferay.modules.template_contexts.template;

import com.axiell.arena.liferay.modules.template_contexts.api.ArenaUtil;
import com.axiell.arena.liferay.modules.template_contexts.api.DynamicListFactoryBuilderFactory;
import com.axiell.arena.liferay.modules.template_contexts.api.FeaturedSearchFactoryBuilderFactory;
import com.axiell.arena.liferay.modules.template_contexts.api.ModelFactoryBuilderFactory;
import com.axiell.arena.liferay.modules.template_contexts.api.StaffPickFactoryBuilderFactory;
import com.axiell.arena.liferay.modules.template_contexts.api.ThemedSelectionFactoryBuilderFactory;
import com.axiell.arena.liferay.modules.template_contexts.service.CentralSettingsCacheService;
import com.axiell.arena.liferay.modules.template_contexts.service.ConfigurationScopeManager;
import com.axiell.arena.liferay.modules.template_contexts.service.FederatedSearchClient;
import com.liferay.portal.kernel.template.TemplateContextContributor;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

@Component(
        immediate = true,
        property = {"type=" + TemplateContextContributor.TYPE_GLOBAL},
        service = TemplateContextContributor.class
)
public class ArenaTemplateContextContributor implements TemplateContextContributor {
    private final Map<String, ModelFactoryBuilderFactory> structureCache = new HashMap<>();
    @Reference
    private FederatedSearchClient federatedSearchClient;
    @Reference
    private ConfigurationScopeManager configurationScopeManager;
    @Reference
    private CentralSettingsCacheService centralSettingsCacheService;

    private static boolean isSignedIn(HttpServletRequest request) {
        String LIFERAY_SHARED_AGENCY_USER = "LIFERAY_SHARED_AGENCY_USER";
        HttpSession session = request.getSession(true);
        Object agencyUser = session.getAttribute(LIFERAY_SHARED_AGENCY_USER);
        boolean isSignedIn = false;
        if (agencyUser != null) {
            Object arenaUserId = getValue(agencyUser, "getId");
            Object patronId = getValue(agencyUser, "getSelectedExternalUserId");
            isSignedIn = (patronId != null || arenaUserId != null);
        }
        return isSignedIn;
    }

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
        try {
            return method.invoke(object);
        } catch (IllegalAccessException | InvocationTargetException ex) {
            return null;
        }
    }

    @Override
    public void prepare(Map<String, Object> contextObjects, HttpServletRequest request) {
        contextObjects.put("ArenaUtil", (ArenaUtil) modelName -> structureCache.get(modelName).assetFactory());
        contextObjects.put("ArenaIsSignedIn", isSignedIn(request));
        contextObjects.put("ArenaConfigurationScopeManager", configurationScopeManager);
        contextObjects.put("ArenaFederatedSearchService", federatedSearchClient);
        contextObjects.put("ArenaCentralSettingsService", centralSettingsCacheService);
    }

    @Reference
    protected void setDynamicListFactoryBuilderFactory(DynamicListFactoryBuilderFactory dynamicListFactoryBuilderFactory) {
        this.structureCache.put(dynamicListFactoryBuilderFactory.getModelName(), dynamicListFactoryBuilderFactory);
    }

    @Reference
    protected void setFeaturedSearchFactoryBuilderFactory(FeaturedSearchFactoryBuilderFactory featuredSearchFactoryBuilderFactory) {
        this.structureCache.put(featuredSearchFactoryBuilderFactory.getModelName(), featuredSearchFactoryBuilderFactory);
    }

    @Reference
    protected void setStaffPickFactoryBuilderFactory(StaffPickFactoryBuilderFactory staffPickFactoryBuilderFactory) {

        this.structureCache.put(staffPickFactoryBuilderFactory.getModelName(), staffPickFactoryBuilderFactory);
    }

    @Reference
    protected void setThemedSelectionFactoryBuilderFactory(ThemedSelectionFactoryBuilderFactory themedSelectionFactoryBuilderFactory) {
        this.structureCache.put(themedSelectionFactoryBuilderFactory.getModelName(), themedSelectionFactoryBuilderFactory);
    }

}
