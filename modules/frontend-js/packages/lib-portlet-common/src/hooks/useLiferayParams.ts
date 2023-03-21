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
