import {History, LocationDescriptorObject} from 'history';

import LiferayUtil from './LiferayUtil';

export default class RoutingUtil {
	static navigatePage(
		page: string,
		locationDescriptionObject: LocationDescriptorObject,
		history: History
	): void {
		const currentPage = LiferayUtil.getCurrentPage();
		const isCurrentPage = currentPage === page;

		if (isCurrentPage) {
			history.push(locationDescriptionObject);
		} else {
			const pagePath = LiferayUtil.getPagePath(page);
			const path = `${pagePath}#${locationDescriptionObject.pathname}${locationDescriptionObject.search}`;
			window.location.assign(path);
		}
	}

	static navigatePageLink(
		page: string,
		locationDescriptionObject: LocationDescriptorObject,
	): string | undefined {
		const currentPage = LiferayUtil.getCurrentPage();
		const isCurrentPage = currentPage === page;

		if (isCurrentPage) {
			return undefined;
		} else {
			const pagePath = LiferayUtil.getPagePath(page);

			return `${pagePath}#${locationDescriptionObject.pathname}${locationDescriptionObject.search}`;
		}
	}

	static getHostName(): string {
		return window.location.hostname;
	}
}
