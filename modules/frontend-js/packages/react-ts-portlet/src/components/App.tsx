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
				<span className={classNames("tag")}>
					{(window as any).Liferay.Language.get('portlet-namespace')}:
				</span>

                <span className="value">{liferayParams.portletNamespace}</span>
            </div>

            <div>
				<span className={classNames("tag")}>
					{(window as any).Liferay.Language.get('context-path')}:
				</span>

                <span className="value">{liferayParams.contextPath}</span>
            </div>

            <div>
				<span className={classNames("tag")}>
					{(window as any).Liferay.Language.get('portlet-element-id')}:
				</span>

                <span className="value">{liferayParams.portletElementId}</span>
            </div>

            <div>
				<span className={classNames("tag")}>
					{(window as any).Liferay.Language.get('configuration')}:
				</span>

                <span className="pre value">{JSON.stringify(liferayParams.configuration, null, 2)}</span>
            </div>

            <div>
                <ExtendedButton label={t("extendedButtonLabel")}/>
            </div>
        </div>
    );
}

export default App;
