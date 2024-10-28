import React from 'react';

import {AlertActions} from '../index';

const AlertActionsContext = React.createContext<React.RefObject<AlertActions>>(
	React.createRef()
);

export default AlertActionsContext;
