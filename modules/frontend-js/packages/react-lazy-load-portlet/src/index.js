import React from 'react';
import ReactDOM from 'react-dom';

import AppComponent from "./AppComponent";
import LiferayUtil from "./LiferayUtil";


const DRINK = 'drink';

const PORTLET_INSTANCE_DEFAULT = {
    [DRINK]: 'orange',
};

export default function main(liferayParams) {
    const liferayParamsWithDefaults = LiferayUtil.setLiferayParamsDefaults(
        liferayParams,
        PORTLET_INSTANCE_DEFAULT,
        undefined
    );
    const portletElement = document.getElementById(liferayParamsWithDefaults.portletElementId);
    const markup = React.createElement(AppComponent, {...liferayParamsWithDefaults});
    ReactDOM.render(LiferayUtil.isPortal() ? (
            <>{markup}</>
        ) : (
            <React.StrictMode>{markup}</React.StrictMode>
        ),
        portletElement
    );
};

if (!LiferayUtil.isPortal()) {
    main(LiferayUtil.LIFERAY_PARAMS_DEFAULT);
}
