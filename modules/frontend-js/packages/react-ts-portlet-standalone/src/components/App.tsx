import React from 'react';
import useLiferayParams from "../hooks/useLiferayParams";

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
