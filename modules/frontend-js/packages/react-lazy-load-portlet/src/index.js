import React from 'react';
import ReactDOM from 'react-dom';

import AppComponent from "./AppComponent";
import LiferayUtil, {LIFERAY_PARAMS_DEFAULT} from "./LiferayUtil";


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
            <React.Fragment>{markup}</React.Fragment>
        ) : (
            <React.StrictMode>{markup}</React.StrictMode>
        ),
        portletElement
    );
    if (LiferayUtil.isPortal()) {
        Liferay.once('destroyPortlet', () => {
            ReactDOM.unmountComponentAtNode(portletElement);
        });
    }
};

if (!LiferayUtil.isPortal()) {
    main(LIFERAY_PARAMS_DEFAULT);
}
