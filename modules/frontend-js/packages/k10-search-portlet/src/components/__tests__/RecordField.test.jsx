import React from 'react';
import {screen} from '@testing-library/dom';
import {render} from '@testing-library/react';

import {RecordField} from '../RecordField';

jest.mock('react-i18next');

describe('RecordField', () => {
    test('value as string', () => {
        const {container} = render(
            <RecordField id="name" t={(key) => key} value="value"/>
        );
        screen.debug();
        let elements = screen.getAllByTestId('field-name');
        expect(elements.length).toBe(1);
        expect(elements[0].innerHTML).toEqual('name');

        elements = screen.getAllByTestId('field-value');
        expect(elements.length).toBe(1);
        expect(elements[0].innerHTML).toEqual('value');
    });

    test('value as array', () => {
        const {container} = render(
            <RecordField
                id="name"
                t={(key) => key}
                value={['value0', 'value1']}
            />
        );
        screen.debug();
        let elements = screen.getAllByTestId('field-name');
        expect(elements.length).toBe(1);
        expect(elements[0].innerHTML).toEqual('name');


        elements = screen.getAllByTestId('field-value');
        expect(elements.length).toBe(1);
        expect(elements[0].innerHTML).toEqual('value0, value1');
    });
});
