import { render, unmountComponentAtNode } from 'react-dom';
import React from 'react';
import App from './App';

class WebComponent extends HTMLElement {
  connectedCallback() {
    render(
      <React.StrictMode>
        <App />
      </React.StrictMode>,
      this
    );
  }

  disconnectedCallback() {
    unmountComponentAtNode(this);
  }
}

const ELEMENT_NAME = 'vacation-test-portlet-element';

if (customElements.get(ELEMENT_NAME)) {
  // eslint-disable-next-line no-console
  console.log(`Skipping registration for <${ELEMENT_NAME}> (already registered)`);
} else {
  customElements.define(ELEMENT_NAME, WebComponent);
}
