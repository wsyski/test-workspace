/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {INITIAL_FILTER} from './constants/initialFilter';

export default function getDateCreatedFilterTerm(
	initialFilter: string,
	activityPeriod: typeof INITIAL_FILTER.submitDate
) {
	const filterDates = [];

	if (activityPeriod.dates.startDate) {
		filterDates.push(
			`submitDate ge ${new Date(
				activityPeriod.dates.startDate
			).toISOString()}`
		);
	}
	if (activityPeriod.dates.endDate) {
		filterDates.push(
			`submitDate le ${new Date(
				activityPeriod.dates.endDate
			).toISOString()}`
		);
	}

	return initialFilter
		.concat(' and ')
		.concat(
			filterDates.join(
				activityPeriod.dates.startDate > activityPeriod.dates.endDate
					? ' or '
					: ' and '
			)
		);
}
