/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
enum ResourceStatus {
	SUCCESS = 'SUCCESS',
	PENDING = 'PENDING',
	ERROR = 'ERROR',
}

export default class SuspensibleResource<T> {
	private status: ResourceStatus = ResourceStatus.PENDING;
	private result: T | undefined;
	private error: Error | undefined;
	private suspender: Promise<void>;

	constructor(private promise: Promise<T>) {
		this.suspender = this.promise.then(
			(r) => {
				this.status = ResourceStatus.SUCCESS;
				this.result = r;
			},
			(error) => {
				this.status = ResourceStatus.ERROR;
				this.error = error;
			}
		);
	}

	read(): T {
		switch (this.status) {
			case ResourceStatus.PENDING: {
				throw this.suspender;
			}
			case ResourceStatus.ERROR: {
				throw this.error;
			}
			default:
				return this.result as T;
		}
	}
}
