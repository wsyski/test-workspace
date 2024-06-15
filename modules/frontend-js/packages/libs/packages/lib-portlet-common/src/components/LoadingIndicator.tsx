/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayLoadingIndicator from '@clayui/loading-indicator';
import React from 'react';

export interface Props {
	isLoading: boolean;
}

const LoadingIndicator: React.FC<Props> = ({isLoading}: Props) => {
	return (
		<>
			{' '}

			{isLoading && <ClayLoadingIndicator />}{' '}
		</>
	);
};

export default LoadingIndicator;
