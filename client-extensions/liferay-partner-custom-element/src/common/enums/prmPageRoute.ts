/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

export enum PRMPageRoute {
	CONFIRMATION_DEAL_REGISTRATION = 'sales/deal-registrations/confirmation',
	CONFIRMATION_MDF_REQUEST = 'marketing/mdf-requests/confirmation',
	CONFIRMATION_MDF_CLAIM = 'marketing/mdf-claims/confirmation',
	CREATE_DEAL_REGISTRATION = 'sales/deal-registrations/new',
	CREATE_MDF_REQUEST = 'marketing/mdf-requests/new',
	DEAL_REGISTRATION_LISTING = 'sales/deal-registrations',
	EDIT_DEAL_REGISTRATION = 'sales/deal-registrations/edit',
	EDIT_MDF_CLAIM = 'marketing/mdf-claims/edit',
	EDIT_MDF_REQUEST = 'marketing/mdf-requests/edit',
	MDF_CLAIM_LISTING = 'marketing/mdf-claims',
	MDF_REQUESTS_LISTING = 'marketing/mdf-requests',
	PARTNER_OPPORTUNITIES_LISTING = 'sales/partner-opportunities',
}
