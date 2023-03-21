import LiferayUtil from '../LiferayUtil';

describe('LiferayUtil', () => {
	test('getLocaleId', () => {
		jest.spyOn(LiferayUtil, 'getLocale').mockImplementation(() => 'en_US');
		const localeId = LiferayUtil.getLocaleId();
		expect(localeId).toEqual('en-US');
	});

	test('parseLayoutRelativeURL all', () => {
		jest.spyOn(LiferayUtil, 'getLayoutRelativeURL').mockImplementation(
			() => '/en_GB/widget/web/arena/welcome'
		);
		const [partLocale, partWidget, partScope, partFriendlyUrl, partPage] =
			LiferayUtil.parseLayoutRelativeURL();
		expect(partLocale).toEqual('/en_GB');
		expect(partWidget).toEqual('/widget');
		expect(partScope).toEqual('/web');
		expect(partFriendlyUrl).toEqual('/arena');
		expect(partPage).toEqual('/welcome');
	});

	test('parseLayoutRelativeURL without locale and widget', () => {
		jest.spyOn(LiferayUtil, 'getLayoutRelativeURL').mockImplementation(
			() => '/web/arena/welcome'
		);
		const [partLocale, partWidget, partScope, partFriendlyUrl, partPage] =
			LiferayUtil.parseLayoutRelativeURL();
		expect(partLocale).toEqual('');
		expect(partWidget).toEqual('');
		expect(partScope).toEqual('/web');
		expect(partFriendlyUrl).toEqual('/arena');
		expect(partPage).toEqual('/welcome');
	});

	test('parseLayoutRelativeURL without group and widget', () => {
		jest.spyOn(LiferayUtil, 'getLayoutRelativeURL').mockImplementation(
			() => '/en_GB/welcome'
		);
		const [partLocale, partWidget, partScope, partFriendlyUrl, partPage] =
			LiferayUtil.parseLayoutRelativeURL();
		expect(partLocale).toEqual('/en_GB');
		expect(partWidget).toEqual('');
		expect(partScope).toEqual('');
		expect(partFriendlyUrl).toEqual('');
		expect(partPage).toEqual('/welcome');
	});

	test('getPagePath without locale and widget', () => {
		jest.spyOn(LiferayUtil, 'getLayoutRelativeURL').mockImplementation(
			() => '/web/arena/welcome'
		);
		const pagePath = LiferayUtil.getPagePath('detail');
		expect(pagePath).toEqual('/web/arena/detail');
	});

	test('getCurrentPage without group and widget', () => {
		jest.spyOn(LiferayUtil, 'getLayoutRelativeURL').mockImplementation(
			() => '/en_GB/welcome'
		);
		const currentPage = LiferayUtil.getCurrentPage();
		expect(currentPage).toEqual('welcome');
	});

	test('getCurrentPage with locale id without group and widget', () => {
		jest.spyOn(LiferayUtil, 'getLayoutRelativeURL').mockImplementation(
			() => '/en-GB/welcome'
		);
		const currentPage = LiferayUtil.getCurrentPage();
		expect(currentPage).toEqual('welcome');
	});

	test('getCurrentPage with 5 levels', () => {
		jest.spyOn(LiferayUtil, 'getLayoutRelativeURL').mockImplementation(
			() => '/web/friendlyUrl/protected/my-account/overview'
		);
		const currentPage = LiferayUtil.getCurrentPage();
		expect(currentPage).toEqual('protected/my-account/overview');
	});

	test('getFriendlyUrl without group and widget', () => {
		jest.spyOn(LiferayUtil, 'getLayoutRelativeURL').mockImplementation(
			() => '/en_GB/welcome'
		);
		const friendlyUrl = LiferayUtil.getFriendlyUrl();
		expect(friendlyUrl).toEqual('/arena');
	});

	test('getFriendlyUrl without locale and widget', () => {
		jest.spyOn(LiferayUtil, 'getLayoutRelativeURL').mockImplementation(
			() => '/web/friendlyUrl/welcome'
		);
		const friendlyUrl = LiferayUtil.getFriendlyUrl();
		expect(friendlyUrl).toEqual('/friendlyUrl');
	});

	test('getFriendlyUrl with 5 levels', () => {
		jest.spyOn(LiferayUtil, 'getLayoutRelativeURL').mockImplementation(
			() => '/web/friendlyUrl/protected/my-account/overview'
		);
		const friendlyUrl = LiferayUtil.getFriendlyUrl();
		expect(friendlyUrl).toEqual('/friendlyUrl');
	});

	test('getLanguage', () => {
		jest.spyOn(LiferayUtil, 'getLocale').mockImplementation(() => 'sv_SE');
		const language = LiferayUtil.getLanguage();
		expect(language).toEqual('sv');
	});

	test('getLanguage with only language', () => {
		jest.spyOn(LiferayUtil, 'getLocale').mockImplementation(() => 'en');
		const language = LiferayUtil.getLanguage();
		expect(language).toEqual('en');
	});

	test('getCountry', () => {
		jest.spyOn(LiferayUtil, 'getLocale').mockImplementation(() => 'sv_SE');
		const country = LiferayUtil.getCountry();
		expect(country).toEqual('SE');
	});

	test('getCountry with only language', () => {
		jest.spyOn(LiferayUtil, 'getLocale').mockImplementation(() => 'en');
		const country = LiferayUtil.getCountry();
		expect(country).toEqual('US');
	});
});
