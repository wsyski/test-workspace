import React from 'react';

import LiferayParamsContext, {LIFERAY_PARAMS_DEFAULT} from "../contexts/LiferayParamsContext";
import { Configuration} from "../index";
import LiferayUtil from "../utils/LiferayUtil";

interface Props {
	children: React.ReactNode;
	portletInstance: Configuration;
}

const WithLiferayParams = ({children, portletInstance}: Props) => {
	const liferayParamsWithDefaults = LiferayUtil.setLiferayParamsDefaults(
		LIFERAY_PARAMS_DEFAULT,
		portletInstance
	);

	return (
		<LiferayParamsContext.Provider value={liferayParamsWithDefaults}>
			{children}
		</LiferayParamsContext.Provider>
	);
};

export default WithLiferayParams;
