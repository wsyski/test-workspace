/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import * as React from 'react';

import LiferayParamsContext from '../contexts/LiferayParamsContext';
import {ExtendedLiferayParams} from '../index';
import ExtendedLiferayParamsImpl from '../models/ExtendedLiferayParamsImpl';

const useLiferayParams = (): ExtendedLiferayParams => {
	const liferayParams = React.useContext(LiferayParamsContext);

	return React.useMemo(
		() => new ExtendedLiferayParamsImpl(liferayParams),
		[liferayParams]
	);
};

export default useLiferayParams;
