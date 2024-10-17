import {TestUtil} from '@arena/lib-portlet-test';
import {shallow} from 'enzyme';
import React from 'react';

import {RecordField} from '../RecordField';

jest.mock('react-i18next');

describe('RecordField', () => {
	test('value as string', () => {
		const wrapper = shallow(
			<RecordField id="name" t={(key) => key} value="value" />
		);
		const fieldName = TestUtil.findShallowByTestAttr(wrapper, 'field-name');
		expect(fieldName.text()).toBe('name');
		const fieldValue = TestUtil.findShallowByTestAttr(
			wrapper,
			'field-value'
		);
		expect(fieldValue.text()).toBe('value');
	});

	test('value as array', () => {
		const wrapper = shallow(
			<RecordField
				id="name"
				t={(key) => key}
				value={['value0', 'value1']}
			/>
		);
		const fieldName = TestUtil.findShallowByTestAttr(wrapper, 'field-name');
		expect(fieldName.text()).toBe('name');
		const fieldValue = TestUtil.findShallowByTestAttr(
			wrapper,
			'field-value'
		);
		expect(fieldValue.text()).toBe('value0, value1');
	});
});
