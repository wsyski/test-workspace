import {ExtendedButton} from "@arena/lib-component-common";
import {useLiferayParams} from "@arena/lib-portlet-common";
import classNames from "classnames";
import React from 'react';
import { useTranslation } from "react-i18next";

const App: React.FC<{}> = () => {
    const { t } = useTranslation();
    const liferayParams = useLiferayParams();

    return (
        <div>
            <div>
				<span className={classNames("tag")}>{t('portletNamespace.label')}:</span>

                <span className="value">{liferayParams.portletNamespace}</span>
            </div>

            <div>
				<span className={classNames("tag")}>{t('contextPath.label')}:</span>

                <span className="value">{liferayParams.contextPath}</span>
            </div>

            <div>
				<span className={classNames("tag")}>{t('portletElementId.label')}:</span>

                <span className="value">{liferayParams.portletElementId}</span>
            </div>

            <div>
				<span className={classNames("tag")}>{t('configuration.label')}:</span>

                <span className="pre value">{JSON.stringify(liferayParams.configuration, null, 2)}</span>
            </div>

            <div>
                <ExtendedButton label={t("extendedButton.label")}/>
            </div>
        </div>
    );
}

export default App;
