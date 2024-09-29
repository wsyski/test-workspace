/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ClayButtonWithIcon} from '@clayui/button';
import {ClayInput} from '@clayui/form';
import {memo, useState} from 'react';

interface IProps {
	initialSearchTerm?: string;
	onSearchSubmit: (term: string) => void;
	urlParams?: URLSearchParams;
}

const Search = ({initialSearchTerm, onSearchSubmit, urlParams}: IProps) => {
	const [term, setTerm] = useState(
		initialSearchTerm ? initialSearchTerm : ''
	);
	const [searching, setSearching] = useState(
		initialSearchTerm ? false : true
	);

	const handleSearchSubmit = () => {
		if (searching) {
			onSearchSubmit(term);
			setSearching(false);

			if (urlParams) {
				if (term) {
					urlParams.set('search', term);

					return;
				}

				urlParams.delete('search');
			}

			return;
		}

		setTerm('');
		onSearchSubmit('');
		setSearching(true);

		if (urlParams) {
			urlParams.delete('search');
		}
	};

	return (
		<ClayInput.Group className="m-0 mr-2">
			<ClayInput.GroupItem>
				<ClayInput
					className="form-control input-group-inset input-group-inset-after"
					onChange={(event) => {
						setTerm(event.target.value);
						setSearching(true);
					}}
					onKeyPress={(event) => {
						if (event.key === 'Enter') {
							handleSearchSubmit();
						}
					}}
					placeholder="Search"
					type="text"
					value={term}
				/>

				<ClayInput.GroupInsetItem after tag="span">
					{searching || !term ? (
						<ClayButtonWithIcon
							aria-label="Search"
							displayType="unstyled"
							onClick={() => handleSearchSubmit()}
							symbol="search"
						/>
					) : (
						<ClayButtonWithIcon
							aria-label="Clean Search"
							className="navbar-breakpoint-d-none"
							displayType="unstyled"
							onClick={() => handleSearchSubmit()}
							symbol="times"
						/>
					)}
				</ClayInput.GroupInsetItem>
			</ClayInput.GroupItem>
		</ClayInput.Group>
	);
};

export default memo(Search);
