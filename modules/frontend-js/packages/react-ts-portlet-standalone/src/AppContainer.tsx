import {ClayIconSpriteContext} from '@clayui/icon';
import React from 'react';

import App from './components/App';
import LiferayParamsContext from './contexts/LiferayParamsContext';
import {AppContainerProps} from "./lib-portlet-common";
import LiferayUtil from "./utils/LiferayUtil";

const AppContainer: React.FC<AppContainerProps> = ({
                                                       liferayParams,
                                                   }: AppContainerProps) => {

    return (
        <div data-test-id="appContainer">
            <ClayIconSpriteContext.Provider value={LiferayUtil.getClaySpritemap()}>
                <LiferayParamsContext.Provider value={liferayParams}>
                    <App/>
                </LiferayParamsContext.Provider>
            </ClayIconSpriteContext.Provider>
        </div>
    );
};

export default AppContainer;
