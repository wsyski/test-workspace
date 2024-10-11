import { render } from "@testing-library/react";
import React from 'react';

import Input from "../Input";

describe("Input", () => {
    it('is truthy', () => {
      expect(Input).toBeTruthy()
    });
});

describe("Input render", () => {
    test("renders the Input component", () => {
        render(<Input onChange={()=> {}} type="text"/>);
    });
});
