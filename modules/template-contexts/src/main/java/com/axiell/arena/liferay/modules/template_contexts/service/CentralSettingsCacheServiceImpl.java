package com.axiell.arena.liferay.modules.template_contexts.service;

import com.axiell.arena.liferay.modules.arena.model.AgencyMember;
import com.axiell.arena.liferay.modules.arena.model.AgencyMemberSummary;
import com.axiell.arena.liferay.modules.arena.model.PortalSite;
import com.axiell.arena.liferay.modules.arena.service.ArenaCentralLocalServiceUtil;
import com.axiell.arena.liferay.modules.template_contexts.model.central.CentralSettingsDto;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.service.GroupLocalService;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

@Component(service = CentralSettingsCacheService.class)
public class CentralSettingsCacheServiceImpl implements CentralSettingsCacheService {
    private static final String PORTAL_URL = "Portal URL";
    private static final String DEFAULT_SOURCE_ID = "Default Source ID";
    private static final String FAVOURITE_AGENCY_MEMBER = "Favourite Agency Member";

    private final LoadingCache<Long, CentralSettingsDto> centralSettingsCache;

    @Reference
    private GroupLocalService groupLocalService;

    @Reference
    private StaticContextService staticContextService;

    public CentralSettingsCacheServiceImpl() {
        this.centralSettingsCache = CacheBuilder.newBuilder()
                .maximumSize(10000)
                .expireAfterWrite(1, TimeUnit.HOURS)
                .build(new CacheLoader<>() {
                    @Override
                    public CentralSettingsDto load(Long groupId) throws PortalException {
                        return loadCentralSettings(groupId);
                    }
                });
    }

    private CentralSettingsDto loadCentralSettings(long groupId) throws PortalException {
        String portalURL = staticContextService.getThemeDisplay().getPortalURL();
        if (portalURL == null) {
            throw new IllegalStateException("Portal URL is null");
        }
        String virtualHostname = portalURL.replaceAll("https?://", "");
        String friendlyUrl = groupLocalService.getGroup(groupId).getFriendlyURL();

        PortalSite portalSite = ArenaCentralLocalServiceUtil.getPortalSite(virtualHostname, friendlyUrl);
        if (portalSite == null) {
            throw new IllegalStateException("PortalSite not found");
        }
        String defaultSourceId = portalSite.getMainGroup().getSubGroups().get(PORTAL_URL).getProperties().get(DEFAULT_SOURCE_ID);
        Map<String, String> mainGroupProperties = portalSite.getMainGroup().getProperties();
        if (mainGroupProperties == null) {
            throw new IllegalStateException("Main group properties are null");
        }

        String favoriteAgencyName = mainGroupProperties.get(FAVOURITE_AGENCY_MEMBER);
        if (favoriteAgencyName == null || favoriteAgencyName.isEmpty()) {
            throw new IllegalStateException("Favorite agency name is not set");
        }

        AgencyMemberSummary favoriteAgencyMember = portalSite.getAgencyMemberSummaries().stream()
                .filter(agencyMember -> favoriteAgencyName.equals(agencyMember.getName()))
                .findAny()
                .orElseThrow(() -> new IllegalStateException("Cannot find favorite agency member"));

        AgencyMember agencyMember = ArenaCentralLocalServiceUtil.getAgencyMember(favoriteAgencyMember.getId());
        if (agencyMember == null) {
            throw new IllegalStateException("Agency member not found");
        }

        CentralSettingsDto centralSettingsDto = new CentralSettingsDto();
        centralSettingsDto.setPortalSiteId(portalSite.getId());
        centralSettingsDto.setDefaultSourceId(defaultSourceId);
        centralSettingsDto.setAgencyMemberId(agencyMember.getId());
        centralSettingsDto.setAgencyName(agencyMember.getAgency().getName());
        centralSettingsDto.setAgencyId(agencyMember.getAgency().getId());

        return centralSettingsDto;
    }

    @Override
    public CentralSettingsDto getCentralSettings(Long groupId) throws ExecutionException {
        return centralSettingsCache.get(groupId);
    }
}