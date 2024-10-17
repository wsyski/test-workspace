package com.axiell.arena.liferay.modules.template_contexts;

import com.axiell.arena.liferay.modules.template_contexts.api.ArenaCentralSettingsCacheServiceBuilderFactory;
import com.axiell.arena.liferay.modules.template_contexts.api.ArenaLocalServiceClientBuilderFactory;
import com.axiell.arena.liferay.modules.template_contexts.api.ArenaUtil;
import com.axiell.arena.liferay.modules.template_contexts.api.CommonServicesConfigBuilderFactory;
import com.axiell.arena.liferay.modules.template_contexts.api.DynamicListFactoryBuilderFactory;
import com.axiell.arena.liferay.modules.template_contexts.api.EventFactoryBuilderFactory;
import com.axiell.arena.liferay.modules.template_contexts.api.FeaturedSearchFactoryBuilderFactory;
import com.axiell.arena.liferay.modules.template_contexts.api.FederatedSearchClientBuilderFactory;
import com.axiell.arena.liferay.modules.template_contexts.api.LibraryBranchFactoryBuilderFactory;
import com.axiell.arena.liferay.modules.template_contexts.api.ModelFactoryBuilderFactory;
import com.axiell.arena.liferay.modules.template_contexts.api.StaffPickFactoryBuilderFactory;
import com.axiell.arena.liferay.modules.template_contexts.api.ThemedSelectionFactoryBuilderFactory;
import com.liferay.portal.kernel.template.TemplateContextContributor;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Pascal Collberg
 */
@Component(
        immediate = true,
        property = {"type=" + TemplateContextContributor.TYPE_GLOBAL},
        service = TemplateContextContributor.class
)
public class ArenaTemplateContextContributor implements TemplateContextContributor {

    private final Map<String, ModelFactoryBuilderFactory> structureCache = new HashMap<>();

    @Override
    public void prepare(Map<String, Object> contextObjects, HttpServletRequest request) {
        contextObjects.put("arenaUtil", (ArenaUtil) modelName -> structureCache.get(modelName).assetFactory());
    }

    @Reference
    protected void setDynamicListFactoryBuilderFactory(DynamicListFactoryBuilderFactory dynamicListFactoryBuilderFactory) {
        this.structureCache.put(dynamicListFactoryBuilderFactory.getModelName(), dynamicListFactoryBuilderFactory);
    }

    @Reference
    protected void setEventFactoryBuilderFactory(EventFactoryBuilderFactory eventFactoryBuilderFactory) {
        this.structureCache.put(eventFactoryBuilderFactory.getModelName(), eventFactoryBuilderFactory);
    }

    @Reference
    protected void setFeaturedSearchFactoryBuilderFactory(FeaturedSearchFactoryBuilderFactory featuredSearchFactoryBuilderFactory) {
        this.structureCache.put(featuredSearchFactoryBuilderFactory.getModelName(), featuredSearchFactoryBuilderFactory);
    }

    @Reference
    protected void setLibraryBranchFactoryBuilderFactory(LibraryBranchFactoryBuilderFactory libraryBranchFactoryBuilderFactory) {
        this.structureCache.put(libraryBranchFactoryBuilderFactory.getModelName(), libraryBranchFactoryBuilderFactory);
    }

    @Reference
    protected void setStaffPickFactoryBuilderFactory(StaffPickFactoryBuilderFactory staffPickFactoryBuilderFactory) {

        this.structureCache.put(staffPickFactoryBuilderFactory.getModelName(), staffPickFactoryBuilderFactory);
    }

    @Reference
    protected void setThemedSelectionFactoryBuilderFactory(ThemedSelectionFactoryBuilderFactory themedSelectionFactoryBuilderFactory) {
        this.structureCache.put(themedSelectionFactoryBuilderFactory.getModelName(), themedSelectionFactoryBuilderFactory);
    }

    @Reference
    protected void setFederatedSearchClientFactoryBuilderFactory(FederatedSearchClientBuilderFactory federatedSearchClientBuilderFactory) {
        this.structureCache.put(federatedSearchClientBuilderFactory.getModelName(), federatedSearchClientBuilderFactory);
    }

    @Reference
    protected void setCommonServicesConfigFactoryBuilderFactory(CommonServicesConfigBuilderFactory commonServicesConfigBuilderFactory) {
        this.structureCache.put(commonServicesConfigBuilderFactory.getModelName(), commonServicesConfigBuilderFactory);
    }

    @Reference
    protected void setArenaLocalServiceClientFactoryBuilderFactory(ArenaLocalServiceClientBuilderFactory arenaLocalServiceClientBuilderFactory) {
        this.structureCache.put(arenaLocalServiceClientBuilderFactory.getModelName(), arenaLocalServiceClientBuilderFactory);
    }

    @Reference
    protected void setArenaCentralSettingsCacheServiceBuilderFactory(ArenaCentralSettingsCacheServiceBuilderFactory arenaCentralSettingsCacheServiceBuilderFactory) {
        this.structureCache.put(arenaCentralSettingsCacheServiceBuilderFactory.getModelName(), arenaCentralSettingsCacheServiceBuilderFactory);
    }
}