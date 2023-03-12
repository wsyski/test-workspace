import React from 'react';
import ReactDOM from 'react-dom';
import LiferayUtil, {LIFERAY_PARAMS_DEFAULT} from "./LiferayUtil";
import AppComponent from "./AppComponent";


var DRINK = 'drink';

var PORTLET_INSTANCE_DEFAULT = {
    [DRINK]: 'orange',
};

export default function main(liferayParams) {
    var liferayParamsWithDefaults = LiferayUtil.setLiferayParamsDefaults(
        liferayParams,
        PORTLET_INSTANCE_DEFAULT,
        undefined
    );
    var portletElement = document.getElementById(liferayParamsWithDefaults.portletElementId);
    var markup = React.createElement(AppComponent, {...liferayParamsWithDefaults});
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
