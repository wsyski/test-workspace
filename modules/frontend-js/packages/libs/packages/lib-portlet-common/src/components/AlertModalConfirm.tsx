/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import ClayModal, {useModal} from '@clayui/modal';
import React from 'react';
import {WithTranslation, withTranslation} from 'react-i18next';

import LiferayUtil from '../utils/LiferayUtil';
import {AlertConfirmProps} from './AlertConfirm';

export interface Props extends WithTranslation, AlertConfirmProps {}

const AlertModalConfirmWithT: React.FC<Props> = ({
	alert,
	onConfirm,
	t,
}: Props) => {
	const {observer, onClose} = useModal({
		onClose: () => {
			onConfirm(alert);
		},
	});
	const title =
		typeof alert.title === 'undefined'
			? t(`lblAlert.${alert.displayType}`)
			: alert.title;

	return (
		<ClayModal
			observer={observer}
			spritemap={LiferayUtil.getClaySpritemap()}
			status={alert.displayType === 'secondary' ? undefined : alert.displayType}
		>
			<ClayModal.Header aria-live="polite">{title}</ClayModal.Header>

			<ClayModal.Body aria-live="polite">{alert.message}</ClayModal.Body>

			<ClayModal.Footer
				first={
					<ClayButton onClick={onClose} title={t('btnOK.title')!}>
						{t('btnOK.label')}
					</ClayButton>
				}
			/>
		</ClayModal>
	);
};

export default withTranslation()(
	AlertModalConfirmWithT
) as React.FC<AlertConfirmProps>;
