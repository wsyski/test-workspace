package com.axiell.arena.liferay.modules.common_services.internal.resource.v1_0;

import com.axiell.arena.liferay.modules.arena.configuration.ArenaGroupConfiguration;
import com.axiell.arena.liferay.modules.arena.configuration.ArenaSystemConfiguration;
import com.axiell.arena.liferay.modules.common_services.configuration.CommonServicesGroupConfiguration;
import com.axiell.arena.liferay.modules.common_services.configuration.CommonServicesSystemConfiguration;
import com.axiell.arena.liferay.modules.common_services.dto.v1_0.GroupConfig;
import com.axiell.arena.liferay.modules.common_services.resource.v1_0.GroupConfigResource;

import com.liferay.portal.configuration.module.configuration.ConfigurationProvider;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ServiceScope;

import javax.validation.constraints.NotNull;

import static com.axiell.arena.liferay.modules.common_services.constants.CommonServicesConstants.HEADER_CACHE_CONTROL;
import static com.axiell.arena.liferay.modules.common_services.constants.CommonServicesConstants.HEADER_CACHE_CONTROL_VALUE;

/**
 * @author Wojciech Syski
 */
@Component(
	properties = "OSGI-INF/liferay/rest/v1_0/group-config.properties",
	scope = ServiceScope.PROTOTYPE, service = GroupConfigResource.class
)
public class GroupConfigResourceImpl extends BaseGroupConfigResourceImpl {
	@Reference
	private ConfigurationProvider _configurationProvider;

	@Override
	public GroupConfig getGroupConfig(@NotNull Integer groupId) throws Exception {
		CommonServicesSystemConfiguration commonServicesSystemConfiguration = _configurationProvider.getSystemConfiguration(CommonServicesSystemConfiguration.class);
		CommonServicesGroupConfiguration commonServicesGroupConfiguration = _configurationProvider.getGroupConfiguration(CommonServicesGroupConfiguration.class, groupId);
		ArenaSystemConfiguration arenaSystemConfiguration = _configurationProvider.getSystemConfiguration(ArenaSystemConfiguration.class);
		ArenaGroupConfiguration arenaGroupConfiguration = _configurationProvider.getGroupConfiguration(ArenaGroupConfiguration.class, groupId);
		return _toGroupConfig(arenaSystemConfiguration, arenaGroupConfiguration, commonServicesSystemConfiguration, commonServicesGroupConfiguration);
	}

	protected GroupConfig _toGroupConfig(
			final ArenaSystemConfiguration arenaSystemConfiguration,
			final ArenaGroupConfiguration arenaGroupConfiguration,
			final CommonServicesSystemConfiguration commonServicesSystemConfiguration,
			final CommonServicesGroupConfiguration commonServicesGroupConfiguration) {
		this.contextHttpServletResponse.addHeader(HEADER_CACHE_CONTROL, HEADER_CACHE_CONTROL_VALUE);
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
