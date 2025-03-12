import {render} from '@testing-library/react'

import React from 'react';

import '@testing-library/jest-dom'

import ArenaIcon, {ArenaIconSpriteContext} from '../ArenaIcon';

describe('ArenaIcon', () => {
    it('renders', () => {
        const {container} = render(
            <ArenaIcon
                spritemap="/path/to/some/resource.svg"
                symbol="cool-icon"
            />
        );

        expect(container).toMatchSnapshot();
    });

    it('renders with context spritemap', () => {
        const {container} = render(
            <ArenaIconSpriteContext.Provider value="foo/bar.svg">
                <ArenaIcon symbol="cool-icon"/>
            </ArenaIconSpriteContext.Provider>
        );

        expect(container).toMatchSnapshot();
    });
});
