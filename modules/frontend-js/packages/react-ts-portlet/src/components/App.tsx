/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useLiferayParams} from "@arena/lib-portlet-common";
import React from 'react';

const App: React.FC<{}> = () => {
    const liferayParams = useLiferayParams();

    return (
        <div>
            <div>
				<span className="tag">
					{// @ts-ignore
                        Liferay.Language.get('portlet-namespace')}:
				</span>

                <span className="value">{liferayParams.portletNamespace}</span>
            </div>

            <div>
				<span className="tag">
					{// @ts-ignore
                        Liferay.Language.get('context-path')}:
				</span>

                <span className="value">{liferayParams.contextPath}</span>
            </div>

            <div>
				<span className="tag">
					{// @ts-ignore
                        Liferay.Language.get('portlet-element-id')}:
				</span>

                <span className="value">{liferayParams.portletElementId}</span>
            </div>

            <div>
				<span className="tag">
					{// @ts-ignore
                        Liferay.Language.get('configuration')}:
				</span>

                <span className="pre value">{JSON.stringify(liferayParams.configuration, null, 2)}</span>
            </div>
        </div>
    );
}

export default App;
