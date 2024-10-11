import { render } from "@testing-library/react";
import React from 'react';

import Button from "../Button";

describe("Button", () => {
  it('is truthy', () => {
    expect(Button).toBeTruthy()
  });
});

describe("Button render", () => {
  test("renders the Button component", () => {
    render(<Button label="Button label" />);
  });
});
