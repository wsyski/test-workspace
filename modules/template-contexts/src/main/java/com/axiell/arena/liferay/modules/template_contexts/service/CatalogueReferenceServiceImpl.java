package com.axiell.arena.liferay.modules.template_contexts.service;

import com.axiell.arena.liferay.modules.template_contexts.model.RecordModel;
import com.axiell.arena.liferay.modules.template_contexts.model.SearchResultsModel;
import com.axiell.arena.liferay.modules.template_contexts.model.central.CentralSettingsDto;
import com.axiell.arena.liferay.modules.template_contexts.model.local.SearchResultsDto;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.util.HttpUtil;
import com.liferay.portal.kernel.util.URLCodec;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Component(
        immediate = true,
        service = CatalogueReferenceService.class
)
public class CatalogueReferenceServiceImpl implements CatalogueReferenceService {

    private static final String CS_ADDRESS = "http://arena-central";
    private static final String LS_ADDRESS = "http://arena-local:16520";
    private static final String CS_BASE = "/central-rest/api/v1/configs";
    private LoadingCache<Long, CentralSettingsDto> centralSettingsCache;
    private GroupLocalService groupLocalService;
    private StaticContextService staticContextService;

    public CatalogueReferenceServiceImpl() {
        centralSettingsCache = CacheBuilder.newBuilder().maximumSize(10000)
                .expireAfterWrite(24, TimeUnit.HOURS)
                .build(new CacheLoader<Long, CentralSettingsDto>() {
                    public CentralSettingsDto load(Long groupId) throws IOException, PortalException {
                        return getServerSettingsFromCentralService(groupId);
                    }
                });
    }

    @SuppressWarnings("unchecked")
    final CentralSettingsDto getServerSettingsFromCentralService(long groupId) throws PortalException, IOException {
        String virtualHostname = staticContextService.getThemeDisplay().getPortalURL().replaceAll("https?://", "");
        String friendlyUrl = groupLocalService.getGroup(groupId).getFriendlyURL();
        // Makes a query to Central REST API to get portal site settings
        Map<String, Object> portalSiteInfo = callCentral(String.format("%s%s/portalsites?vhost=%s&friendlyUrl=%s",
                CS_ADDRESS, CS_BASE, URLCodec.encodeURL(virtualHostname), URLCodec.encodeURL(friendlyUrl)));
        Integer portalSiteID = (Integer) portalSiteInfo.get("id");
        Map<String, Object> mainGroup = (Map<String, Object>) portalSiteInfo.get("mainGroup");
        Map<String, Object> properties = (Map<String, Object>) mainGroup.get("properties");
        String favoriteAgencyName = (String) properties.get("Favourite Agency Member");
        if (favoriteAgencyName == null || favoriteAgencyName.isEmpty()) {
            throw new IllegalStateException("Favorite agency name is not set");
        }

        // Go through portalSites.agencyMemberSummaries to find the one with matching favoriteAgencyName
        List<Map<String, Object>> agencyMemberSummaries = (List<Map<String, Object>>) portalSiteInfo.get("agencyMemberSummaries");
        Map<String, Object> favoriteAgencyMember = agencyMemberSummaries.stream()
                .filter(agencyMember -> favoriteAgencyName.equals(agencyMember.get("name")))
                .findAny().orElseThrow(() -> new IllegalStateException("Can not find favorite agency member"));

        // Makes a query to Central REST API to get Member settings to extract Agency ID from agencyMemberID
        Map<String, Object> agencyMember = callCentral(String.format("%s%s/agencymembers/%s",
                CS_ADDRESS, CS_BASE, favoriteAgencyMember.get("id")));
        Map<String, Object> agency = (Map<String, Object>) agencyMember.get("agency");
        CentralSettingsDto centralSettingsDto = new CentralSettingsDto();
        centralSettingsDto.setAgencyId((Integer) agency.get("id"));
        centralSettingsDto.setAgencyMemberId((Integer) favoriteAgencyMember.get("id"));
        centralSettingsDto.setAgencyName((String) agency.get("name"));
        centralSettingsDto.setPortalSiteId(portalSiteID);
        return centralSettingsDto;
    }

    @SuppressWarnings("unchecked")
    final Map<String, Object> callCentral(String location) throws IOException {
        String response = staticContextService.URLtoString(location);
        Map<String, Object> result = JSONFactoryUtil.looseDeserialize(response, Map.class);
        if (result.containsKey("cause")) {
            throw new IOException((String) result.get("cause"));
        } else {
            return result;
        }
    }

    @Override
    public String getCoverImagePath(long recordId, long groupId) throws ExecutionException {
        CentralSettingsDto centralSettingsDto = centralSettingsCache.get(groupId);
        String virtualHostname = staticContextService.getThemeDisplay().getPortalURL();
        return String.format("%s/local-rest/api/v1/portalsites/%d/agencies/%d/records/%s/cover",
                virtualHostname,
                centralSettingsDto.getPortalSiteId(),
                centralSettingsDto.getAgencyId(),
                recordId);
    }

    @Override
    public String getSearchUrl(String searchPath, String query, long groupId) throws PortalException {
        String virtualHostname = staticContextService.getThemeDisplay().getPortalURL();
        String friendlyUrl = groupLocalService.getGroup(groupId).getFriendlyURL();

        return virtualHostname +
                "/web" +
                friendlyUrl +
                "/" +
                searchPath +
                "?p_r_p_arena_urn%3Aarena_search_query=" +
                URLCodec.encodeURL(query);
    }

    @Override
    public String getAuthorSearchUrl(String searchPath, long groupId, String authorName) throws PortalException {
        String virtualHostname = staticContextService.getThemeDisplay().getPortalURL().replaceAll("https?://", "");
        String friendlyUrl = groupLocalService.getGroup(groupId).getFriendlyURL();
        return virtualHostname +
                "/web" +
                friendlyUrl +
                "/" +
                searchPath +
                "?p_p_id=searchPath_WAR_arenaportlet&p_p_lifecycle=1&p_p_state=normal" +
                "&p_r_p_arena_urn%3Aarena_facet_queries=&p_r_p_arena_urn%3Aarena_search_query=author:" +
                authorName +
                "&p_r_p_arena_urn%3Aarena_search_type=solr";
    }

    @Override
    public String getCrdUrl(String resultPath, long recordId, long groupId) throws PortalException, ExecutionException {
        CentralSettingsDto centralSettingsDto = centralSettingsCache.get(groupId);
        String virtualHostname = staticContextService.getThemeDisplay().getPortalURL();
        String friendlyUrl = groupLocalService.getGroup(groupId).getFriendlyURL();

        return virtualHostname +
                "/web" +
                friendlyUrl +
                "/" +
                resultPath +
                "?p_p_id=crDetailWicket_WAR_arenaportlet&p_p_lifecycle=1&p_p_state=normal&p_r_p_arena_urn%3Aarena_search_item_id=" +
                recordId +
                "&p_r_p_arena_urn%3Aarena_agency_name=" +
                centralSettingsDto.getAgencyName();
    }

    @Override
    public String getCrdUrl(long recordId, long groupId) throws PortalException, ExecutionException {
        return getCrdUrl("results", recordId, groupId);
    }

    @Override
    public List<RecordModel> getRecords(String query, int limit, long groupId, String sortField, String sortDirection) throws IOException, ExecutionException {
        CentralSettingsDto centralSettingsDto = centralSettingsCache.get(groupId);
        String fullRequestUrl = String.format("%s/local-rest/api/v1/portalsites/%d/records?query=%s&count=%d&isShowExtended=false" +
                        "&sortDirection=" + sortDirection + "&sortField=" + sortField + "&agencyMemberId=%d&decorationNames=Ratings",
                LS_ADDRESS, centralSettingsDto.getPortalSiteId(),
                URLCodec.encodeURL(query), limit, centralSettingsDto.getAgencyMemberId());
        System.out.println(fullRequestUrl);
        String response = HttpUtil.URLtoString(fullRequestUrl);
        SearchResultsDto searchResultsDto = JSONFactoryUtil.looseDeserialize(response, SearchResultsDto.class);
        SearchResultsModel searchResultsModel = new SearchResultsModel();
        searchResultsModel.setTotal(searchResultsDto.getTotal());
        searchResultsModel.setRecords(searchResultsDto.getRecords().stream().map(r -> {
            RecordModel recordModel = new RecordModel();
            recordModel.setCatalogueReferenceService(this);
            recordModel.setFields(r.getFields());
            recordModel.setId(r.getId());
            recordModel.setRating(r.getRating());
            recordModel.setUberkey(r.getUberkey());
            return recordModel;
        }).collect(Collectors.toList()));
        return searchResultsModel.getRecords();
    }

    public List<RecordModel> getRecords(String query, int limit, long groupId) throws IOException, ExecutionException {
        return getRecords(query, limit, groupId, "Relevance", "Descending");
    }

    @Override
    public RecordModel getRecord(long recordId, long groupId, String sortField, String sortDirection) throws IOException, ExecutionException {
        List<RecordModel> records = getRecords(String.format("id:%d", recordId), 1, groupId, sortField, sortDirection);
        return records.stream().findFirst().orElse(null);
    }

    @Override
    public RecordModel getRecord(long recordId, long groupId) throws IOException, ExecutionException {
        List<RecordModel> records = getRecords(String.format("id:%d", recordId), 1, groupId);
        return records.stream().findFirst().orElse(null);
    }

    @Reference
    public void setGroupLocalService(GroupLocalService groupLocalService) {
        this.groupLocalService = groupLocalService;
    }

    @Reference
    public void setStaticContextService(StaticContextService staticContextService) {
        this.staticContextService = staticContextService;
    }

    void setCentralSettingsCache(LoadingCache<Long, CentralSettingsDto> centralSettingsCache) {
        this.centralSettingsCache = centralSettingsCache;
    }


}
