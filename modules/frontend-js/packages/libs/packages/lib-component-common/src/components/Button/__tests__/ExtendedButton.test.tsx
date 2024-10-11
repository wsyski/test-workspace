import { render } from "@testing-library/react";
import React from 'react';

import ExtendedButton from "../ExtendedButton";

describe("Button", () => {
  it('is truthy', () => {
    expect(ExtendedButton).toBeTruthy()
  });
});

describe("Button render", () => {
  test("renders the Button component", () => {
    render(<ExtendedButton label="Button label" />);
  });
});
