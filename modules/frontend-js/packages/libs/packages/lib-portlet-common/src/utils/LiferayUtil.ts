import {Configuration, LiferayParams} from '../index';

const RELATIVE_URL_PATTERN =
	/^(\/\w{2}(?:[_-]\w{2})?)?(\/widget)?(?:(\/group|\/user|\/web)(\/[^/]+))?(\/.+)?$/;
const DEFAULT_FRIENDLY_URL = '/arena';
const DEFAULT_COUNTRY = 'US';

export default class LiferayUtil {
	static getLayoutRelativeURL(): string {
		return (window as any).Liferay.ThemeDisplay.getLayoutRelativeURL();
	}

	static parseLayoutRelativeURL(): [string, string, string, string, string] {
		const layoutRelativeURL = LiferayUtil.getLayoutRelativeURL();
		const parts = layoutRelativeURL.match(RELATIVE_URL_PATTERN);
		if (parts && parts.length === 6) {
			return [
				parts[1] || '',
				parts[2] || '',
				parts[3] || '',
				parts[4] || '',
				parts[5] || '',
			];
		} else {
			throw new Error(
				'Unparseable layout relative url: ' + layoutRelativeURL
			);
		}
	}

	static getPagePath(page: string) {
		const [partLocale, partWidget, partScope, partFriendlyUrl] =
			LiferayUtil.parseLayoutRelativeURL();

		return `${partLocale}${partWidget}${partScope}${partFriendlyUrl}/${page}`;
	}

	static getCurrentPage() {
		const [, , , , partPage] = LiferayUtil.parseLayoutRelativeURL();

		return partPage === '' ? '' : partPage.substring(1);
	}

	static getFriendlyUrl() {
		const [, , , partFriendlyUrl] = LiferayUtil.parseLayoutRelativeURL();

		return partFriendlyUrl === '' ? DEFAULT_FRIENDLY_URL : partFriendlyUrl;
	}

	static getLocale(): string {
		let locale = (window as any).Liferay?.ThemeDisplay.getLanguageId();
		if (locale === 'nn_NO') {
			locale = 'nb_NO';
		}

		return locale;
	}

	static getLocaleId(): string {
		return LiferayUtil.getLocale()?.replace('_', '-');
	}

	static getLanguage(): string {
		const localeId = LiferayUtil.getLocaleId();
		const i = localeId.indexOf('-');

		return i === -1 ? localeId : localeId.substring(0, i);
	}

	static getCountry(): string {
		const localeId = LiferayUtil.getLocaleId();
		const i = localeId.indexOf('-');

		return i === -1 ? DEFAULT_COUNTRY : localeId.substring(i + 1);
	}

	static getPathContext(): string {
		return (window as any).Liferay.ThemeDisplay.getPathContext();
	}

	static getPathThemeImages(): string {
		return (window as any).Liferay.ThemeDisplay.getPathThemeImages();
	}

	static getPortalURL(): string {
		return (window as any).Liferay.ThemeDisplay.getPortalURL();
	}

	static getScopeGroupId(): number {
		return (window as any).Liferay.ThemeDisplay.getScopeGroupId();
	}

	static getClaySpritemap(): string {
		return `${LiferayUtil.getPathThemeImages()}/lexicon/icons.svg`;
	}

	static isLiferaySignedIn(): boolean {
		return (window as any).Liferay.ThemeDisplay.isSignedIn();
	}

	static component: (id: string, value?: any, componentConfig?: any) => any =
		(window as any).Liferay?.component;

	static setLiferayParamsDefaults(
		liferayParams: LiferayParams,
		portletInstance: Configuration | undefined,
		system: Configuration | undefined = undefined
	): LiferayParams {
		let liferayParamsConfiguration = liferayParams.configuration;
		if (portletInstance) {
			liferayParamsConfiguration = {
				...liferayParamsConfiguration,
				portletInstance: {
					...portletInstance,
					...liferayParamsConfiguration.portletInstance,
				},
			};
		}
		if (system) {
			liferayParamsConfiguration = {
				...liferayParamsConfiguration,
				system: {...system, ...liferayParamsConfiguration.system},
			};
		}

		return {...liferayParams, configuration: liferayParamsConfiguration};
	}


}
