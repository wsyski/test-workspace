import React from 'react';

import {CommonServicesConfig} from '../index';

export const COMMON_SERVICES_CONFIG_DEFAULT: CommonServicesConfig = {
	calendarApiEndpoint: 'https://test.axiell.io/api/calendar-event/latest/api',
	calendarCustomerId: '',
	calendarDefaultAllowedAttendees: 0,
	calendarLocationVocabularyId: 0,
	calendarTargetAudienceVocabularyId: 0,
	coversApiEndpoint: "https://test-api.axiell.com/cover/api",
	coversCustomerId : '',
	federatedSearchCustomerAlias: '',
	federatedSearchSourceConfig: {
		defaultSourceId: '',
		sources: [],
	},
	googleAnalyticsMeasurementId: '',
	openingHoursApiEndpoint: 'https://dev.axiell.io/api/openinghours/latest',
	openingHoursCustomerId: '',
	transactionTenantId: '',
};

const CommonServicesConfigContext = React.createContext<CommonServicesConfig>(
	COMMON_SERVICES_CONFIG_DEFAULT
);

export default CommonServicesConfigContext;
