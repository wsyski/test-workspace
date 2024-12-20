import MiscUtil from '../MiscUtil';

describe('MiscUtil', () => {
	test('appendAll nonempty', () => {
		const target = new URLSearchParams();
		target.append('key0', 'value0');
		target.append('key0', 'value1');
		const source = new URLSearchParams();
		target.append('key0', 'value2');
		target.append('key1', 'value3');
		MiscUtil.appendAll(target, source);
		expect(target.toString()).toEqual(
			'key0=value0&key0=value1&key0=value2&key1=value3'
		);
	});

	test('appendAll empty source', () => {
		const target = new URLSearchParams();
		target.append('key0', 'value0');
		target.append('key0', 'value1');
		const source = new URLSearchParams();
		MiscUtil.appendAll(target, source);
		expect(target.toString()).toEqual('key0=value0&key0=value1');
	});

	test('appendAll empty target', () => {
		const target = new URLSearchParams();
		const source = new URLSearchParams();
		target.append('key0', 'value2');
		target.append('key1', 'value3');
		MiscUtil.appendAll(target, source);
		expect(target.toString()).toEqual('key0=value2&key1=value3');
	});

	test('isEmpty URLSearchParams true', () => {
		const urlSearchParams = new URLSearchParams();
		expect(MiscUtil.isEmpty(urlSearchParams)).toEqual(true);
	});

	test('isEmpty URLSearchParams false', () => {
		const urlSearchParams = new URLSearchParams();
		urlSearchParams.append('key0', 'value0');
		expect(MiscUtil.isEmpty(urlSearchParams)).toEqual(false);
	});

	test('isEmpty boolean false', () => {
		const value= false;
		expect(MiscUtil.isEmpty(value)).toEqual(false);
	});

	test('isEqual undefined parameters', () => {
		expect(MiscUtil.isEqual(undefined, undefined)).toEqual(true);
	});

	test('recordValues', () => {
		expect(
			MiscUtil.recordValues({
				key0: ['value0_0', 'value0_1'],
				key1: ['value1_0', 'value1_1'],
			})
		).toEqual(['value0_0', 'value0_1', 'value1_0', 'value1_1']);
	});

	test('randomString', () => {
		const id= MiscUtil.randomString();
		expect(id.length).toEqual(10);
	});

	test('randomString len=10', () => {
		const len = 10;
		const id= MiscUtil.randomString(len);
		expect(id.length).toEqual(len);
	});

	test('normalize', () => {
		expect(MiscUtil.normalize("Crème Brulée")).toEqual("Creme Brulee");
		expect(MiscUtil.normalize("Ste-Gême Family Papers")).toEqual("Ste-Geme Family Papers");
	});

	test('sanitize', () => {
		expect(MiscUtil.sanitize("123<a href='javascript:alert(1)'>I am a dolphin!</a>", {})).toEqual("123<a>I am a dolphin!</a>");
		expect(MiscUtil.sanitize("<img src='javascript:while(1){}'>", {})).toEqual("<img>");
		expect(MiscUtil.sanitize("<p>I'm a pinguin</p>", {})).toEqual("<p>I'm a pinguin</p>");
	});

	test('ishtml', () => {
		expect(MiscUtil.isHtml("<p>I'm a pinguin</p>")).toEqual(true);
		expect(MiscUtil.isHtml("I'm a pinguin")).toEqual(false);
	});
});
