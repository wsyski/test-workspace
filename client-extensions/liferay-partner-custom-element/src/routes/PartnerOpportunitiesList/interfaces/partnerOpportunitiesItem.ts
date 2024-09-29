/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {PartnerOpportunitiesColumnKey} from '../../../common/enums/partnerOpportunitiesColumnKey';

type PartnerOpportunitiesItem = {
	[key in PartnerOpportunitiesColumnKey]?:
		| string
		| boolean
		| undefined
		| number;
};
export default PartnerOpportunitiesItem;
