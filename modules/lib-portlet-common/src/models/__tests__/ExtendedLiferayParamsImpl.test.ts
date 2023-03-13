import {getBoolean} from '../ExtendedLiferayParamsImpl';

describe('ExtendedLiferayParamsImpl', () => {
	test('getBoolean true', () => {
		const value = getBoolean('true');
		expect(value).toEqual(true);
	});

	test('getBoolean false', () => {
		const value = getBoolean('false');
		expect(value).toEqual(false);
	});
});
