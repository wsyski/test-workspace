/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {
	ALERT_ORIGIN_DEFAULT,
	Alert,
	AlertActions,
	AlertActionsContext,
	AlertMessages,
	AlertUtil,
	AppContainerProps,
	ErrorBoundary,
	LiferayParamsContext,
	LiferayUtil,
} from '@arena/lib-portlet-common';
import {ClayIconSpriteContext} from '@clayui/icon';
import React, {Suspense, useImperativeHandle, useRef} from 'react';

import App from './components/App';

interface AlertPanelProps {}

const AlertPanel = React.forwardRef(
	(props: AlertPanelProps, ref: React.Ref<AlertActions>) => {
		const [alerts, setAlerts] = React.useState<Alert[]>([]);

		const alertActions = AlertUtil.getAlertActions(setAlerts);

		useImperativeHandle(ref, () => alertActions);

		const onConfirm = React.useCallback(
			(alert: Alert) => {
				switch (alert.origin) {
					case ALERT_ORIGIN_DEFAULT:
						alertActions.deleteAlert(alert);
						break;
					default:
						throw new Error(
							'Unexpected alert origin: ' + alert.origin
						);
				}
			},
			[alertActions]
		);

		return <AlertMessages alerts={[...alerts]} onConfirm={onConfirm} />;
	}
);

const AppContainer: React.FC<AppContainerProps> = ({
													   liferayParams,
												   }: AppContainerProps) => {
	const alertActionsRef = useRef<AlertActions>(null);

	return (
		<div data-testid="appContainer">
			<ClayIconSpriteContext.Provider
				value={LiferayUtil.getClaySpritemap()}
			>
				<LiferayParamsContext.Provider value={liferayParams}>
					<Suspense fallback={<></>}>
						<AlertPanel ref={alertActionsRef} />

						<ErrorBoundary>
							<AlertActionsContext.Provider
								value={alertActionsRef}
							>
								<App />
							</AlertActionsContext.Provider>
						</ErrorBoundary>
					</Suspense>
				</LiferayParamsContext.Provider>
			</ClayIconSpriteContext.Provider>
		</div>
	);
};

export default AppContainer;
