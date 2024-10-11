import { render } from "@testing-library/react";
import React from 'react';

import ExtendedInput from "../ExtendedInput";

describe("Input", () => {
    it('is truthy', () => {
      expect(ExtendedInput).toBeTruthy()
    });
});

describe("Input render", () => {
    test("renders the Input component", () => {
        render(<ExtendedInput label="Input label"/>);
    });
});
