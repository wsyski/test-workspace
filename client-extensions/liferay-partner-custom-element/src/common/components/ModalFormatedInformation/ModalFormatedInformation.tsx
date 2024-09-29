/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

interface ModalFormatedInformationProps {
	className?: string;
	information?: string;
	label: string;
}

const ModalFormatedInformation = ({
	className = '',
	information,
	label,
}: ModalFormatedInformationProps) => (
	<div className={className}>
		<h2 className="text-paragraph">{label}</h2>

		{information && (
			<p className="align-items-center ml-3 rounded text-paragraph-md">
				{information}
			</p>
		)}
	</div>
);

export default ModalFormatedInformation;
