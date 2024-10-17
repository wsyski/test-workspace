package com.axiell.arena.liferay.modules.template_contexts.service;

import com.axiell.arena.liferay.modules.arena.model.LMSSearchResultResponse;
import com.axiell.arena.liferay.modules.arena.service.ArenaLocalLocalServiceUtil;
import com.axiell.arena.liferay.modules.template_contexts.model.central.CentralSettingsDto;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

@Component(service = ArenaLocalServiceClient.class)
public class ArenaLocalServiceClientImpl implements ArenaLocalServiceClient {
    @Reference
    private CentralSettingsCacheService centralSettingsCacheService;
    @Reference
    private StaticContextService staticContextService;

    @Override
    public LMSSearchResultResponse getLMSSearchResult(String query, String type, Integer start, Integer size) throws Exception {
        long groupId = staticContextService.getThemeDisplay().getScopeGroupId();
        CentralSettingsDto centralSettingsDto = centralSettingsCacheService.getCentralSettings(groupId);
        return ArenaLocalLocalServiceUtil.getLMSSearch(centralSettingsDto.getAgencyMemberId(), query, type, start, size);
    }
}
