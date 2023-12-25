import React from 'react';

export default function AppComponent(props) {
	return (
		<div>
			<div>
				<span className="tag">
					{Liferay.Language.get('portlet-namespace')}:
				</span>

				<span className="value">{props.portletNamespace}</span>
			</div>

			<div>
				<span className="tag">
					{Liferay.Language.get('context-path')}:
				</span>

				<span className="value">{props.contextPath}</span>
			</div>

			<div>
				<span className="tag">
					{Liferay.Language.get('portlet-element-id')}:
				</span>

				<span className="value">{props.portletElementId}</span>
			</div>

			<div>
				<span className="tag">
					{Liferay.Language.get('configuration')}:
				</span>

				<span className="pre value">{JSON.stringify(props.configuration, null, 2)}</span>
			</div>
		</div>
	);
}
