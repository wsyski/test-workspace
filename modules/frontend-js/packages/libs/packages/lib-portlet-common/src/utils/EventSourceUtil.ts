/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import EventSourceCloseToken from '../models/EventSourceCloseToken';

export default class EventSourceUtil {
	static eventSourceFactory(
		url: string,
		eventSourceCloseToken: EventSourceCloseToken
	): EventSource {
		const eventSource = new EventSource(url);
		eventSourceCloseToken.add(eventSource);

		return eventSource;
	}
}
