import * as React from 'react';

import LiferayParamsContext from '../contexts/LiferayParamsContext';
import ExtendedLiferayParamsImpl from '../models/ExtendedLiferayParamsImpl';
import {ExtendedLiferayParams} from "../lib-portlet-common";

const useLiferayParams = (): ExtendedLiferayParams => {
	const liferayParams = React.useContext(LiferayParamsContext);

	return React.useMemo(
		() => new ExtendedLiferayParamsImpl(liferayParams),
		[liferayParams]
	);
};

export default useLiferayParams;
