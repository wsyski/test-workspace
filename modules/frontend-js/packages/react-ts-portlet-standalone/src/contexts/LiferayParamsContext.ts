import * as React from 'react';

import {LIFERAY_PARAMS_DEFAULT} from "../constants/LiferayParamsConstants";

const LiferayParamsContext = React.createContext(LIFERAY_PARAMS_DEFAULT);

export default LiferayParamsContext;
