import classNames from 'classnames';
import React from 'react';
import warning from 'warning';

export const ARENA_ICON_SPRITEMAP = '/o/images-provider/images/icons/icons.svg';
export const ArenaIconSpriteContext = React.createContext('');

interface Props extends React.SVGAttributes<SVGSVGElement> {
	className?: string;

	/**
	 * Path to the location of the spritemap resource.
	 */
	spritemap?: string;

	/**
	 * The id of the icon in the spritemap.
	 */
	symbol: string;
}

const ArenaIcon = React.forwardRef<SVGSVGElement, Props>(
	({className, spritemap, symbol, ...otherProps}: Props, ref) => {
		let spriteMapVal = React.useContext(ArenaIconSpriteContext);

		if (spritemap) {
			spriteMapVal = spritemap;
		}

		warning(
			spriteMapVal,
			'ArenaIcon requires a `spritemap` via prop or ArenaIconSpriteContext'
		);

		return (
			<svg
				{...otherProps}
				className={classNames(
					`lexicon-icon lexicon-icon-${symbol}`,
					className
				)}
				key={symbol}
				ref={ref}
				role="presentation"
			>
				<use xlinkHref={`${spriteMapVal}#${symbol}`} />
			</svg>
		);
	}
);

ArenaIcon.displayName = 'ArenaIcon';

export default ArenaIcon;
