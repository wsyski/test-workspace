package com.axiell.arena.liferay.modules.common_services.internal.resource.v5_7;

import com.axiell.arena.liferay.modules.arena.configuration.ArenaGroupConfiguration;
import com.axiell.arena.liferay.modules.arena.configuration.ArenaSystemConfiguration;
import com.axiell.arena.liferay.modules.common_services.configuration.CommonServicesGroupConfiguration;
import com.axiell.arena.liferay.modules.common_services.configuration.CommonServicesSystemConfiguration;
import com.axiell.arena.liferay.modules.common_services.dto.v5_7.GroupConfig;
import com.axiell.arena.liferay.modules.common_services.resource.v5_7.GroupConfigResource;
import com.liferay.portal.configuration.module.configuration.ConfigurationProvider;
import lombok.CustomLog;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ServiceScope;

import javax.validation.constraints.NotNull;
import javax.ws.rs.core.CacheControl;
import javax.ws.rs.core.EntityTag;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

import static com.axiell.arena.liferay.modules.common_services.constants.CommonServicesConstants.HEADER_CACHE_MAX_AGE;
import static com.axiell.arena.liferay.modules.common_services.constants.CommonServicesConstants.HEADER_EXPIRES_DAYS;

@CustomLog
@Component(
        properties = "OSGI-INF/liferay/rest/v5_7/group-config.properties",
        scope = ServiceScope.PROTOTYPE, service = GroupConfigResource.class
)
public class GroupConfigResourceImpl extends BaseGroupConfigResourceImpl {

    @Reference
    private ConfigurationProvider _configurationProvider;

    private Response.ResponseBuilder evaluatePreconditions(final EntityTag eTag) {
        String ifNonMatch = this.contextHttpServletRequest.getHeader(HttpHeaders.IF_NONE_MATCH);
        if (ifNonMatch != null) {

            boolean result = "*".equals(ifNonMatch);
            if (!result) {
                EntityTag requestTag = EntityTag.valueOf(ifNonMatch);
                result = requestTag.equals(eTag);
            }
            if (result) {
                Response.Status status = Response.Status.NOT_MODIFIED;
                return Response.status(status).tag(eTag);
            }
        }
        return null;
    }

    @Override
    public Response getGroupConfig(@NotNull Integer groupId) throws Exception {
        CommonServicesSystemConfiguration commonServicesSystemConfiguration = _configurationProvider.getSystemConfiguration(CommonServicesSystemConfiguration.class);
        CommonServicesGroupConfiguration commonServicesGroupConfiguration = _configurationProvider.getGroupConfiguration(CommonServicesGroupConfiguration.class, groupId);
        ArenaSystemConfiguration arenaSystemConfiguration = _configurationProvider.getSystemConfiguration(ArenaSystemConfiguration.class);
        ArenaGroupConfiguration arenaGroupConfiguration = _configurationProvider.getGroupConfiguration(ArenaGroupConfiguration.class, groupId);
        GroupConfig groupConfig = _toGroupConfig(arenaSystemConfiguration, arenaGroupConfiguration, commonServicesSystemConfiguration, commonServicesGroupConfiguration);
        CacheControl cacheControl = new CacheControl();
        cacheControl.setNoStore(false);
        cacheControl.setNoTransform(false);
        cacheControl.setPrivate(false);
        cacheControl.setNoCache(false);
        cacheControl.setMaxAge(HEADER_CACHE_MAX_AGE);
        cacheControl.setMustRevalidate(true);
        EntityTag eTag = new EntityTag(String.valueOf(groupConfig.hashCode()));
        Response.ResponseBuilder builder = evaluatePreconditions(eTag);
        if (builder != null) {
            return builder.cacheControl(cacheControl).build();
        }
        Date expires = Date.from(Instant.now().plus(HEADER_EXPIRES_DAYS, ChronoUnit.DAYS));
        return Response.ok(groupConfig).tag(eTag).cacheControl(cacheControl).expires(expires).build();
    }

    protected GroupConfig _toGroupConfig(
            final ArenaSystemConfiguration arenaSystemConfiguration,
            final ArenaGroupConfiguration arenaGroupConfiguration,
            final CommonServicesSystemConfiguration commonServicesSystemConfiguration,
            final CommonServicesGroupConfiguration commonServicesGroupConfiguration) {
        return new GroupConfig() {{
            calendarApiEndpoint = commonServicesSystemConfiguration.calendarApiEndpoint();
            calendarCustomerId = commonServicesGroupConfiguration.calendarCustomerId();
            calendarLocationVocabularyId = commonServicesGroupConfiguration.calendarLocationVocabularyId();
            calendarTargetAudienceVocabularyId = commonServicesGroupConfiguration.calendarTargetAudienceVocabularyId();
            calendarDefaultAllowedAttendees = commonServicesGroupConfiguration.calendarDefaultAllowedAttendees();
            coversApiEndpoint = commonServicesSystemConfiguration.coversApiEndpoint();
            coversCustomerId = commonServicesGroupConfiguration.coversCustomerId();
            federatedSearchApiEndpoint = arenaSystemConfiguration.federatedSearchApiEndpoint();
            federatedSearchCustomerAlias = arenaGroupConfiguration.federatedSearchCustomerAlias();
            federatedSearchSourceConfig = arenaGroupConfiguration.federatedSearchSourceConfig();
            openingHoursApiEndpoint = commonServicesSystemConfiguration.openingHoursApiEndpoint();
            openingHoursCustomerId = commonServicesGroupConfiguration.openingHoursCustomerId();
            transactionApiEndpoint = arenaSystemConfiguration.transactionApiEndpoint();
            transactionTenantId = arenaGroupConfiguration.transactionTenantId();
            googleAnalyticsMeasurementId = commonServicesGroupConfiguration.googleAnalyticsMeasurementId();
        }};
    }

}
