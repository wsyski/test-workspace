/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
export default class EventSourceCloseToken {
	private eventSources: EventSource[] = [];

	constructor() {}

	public add(eventSource: EventSource): void {
		this.eventSources.push(eventSource);
	}

	public close(): void {
		this.eventSources.forEach((eventSource) => eventSource.close());
		this.eventSources = [];
	}
}
