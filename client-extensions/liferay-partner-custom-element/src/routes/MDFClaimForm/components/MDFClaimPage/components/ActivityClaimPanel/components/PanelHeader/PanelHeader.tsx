/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayPanel from '@clayui/panel';
import classNames from 'classnames';

interface IProps {
	children?: React.ReactNode;
	expanded: boolean;
	onClick?: React.MouseEventHandler<HTMLDivElement>;
}

const PanelHeader = ({children, expanded, onClick}: IProps) => (
	<ClayPanel.Title
		aria-expanded={expanded}
		className={classNames(
			'align-items-center card-interactive card-type-template collapse-icon collapse-icon-top d-flex panel-header panel-header-link pl-4 pr-5 py-4',
			{
				collapsed: !expanded,
				show: expanded,
			}
		)}
		onClick={onClick}
		role="tab"
	>
		{children}
	</ClayPanel.Title>
);

export default PanelHeader;
