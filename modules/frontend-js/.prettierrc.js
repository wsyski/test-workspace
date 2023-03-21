const FALLBACK_CONFIG = {
    "bracketSpacing": false,
    "endOfLine": "lf",
    "jsxSingleQuote": false,
    "quoteProps": "consistent",
    "singleQuote": true,
    "tabWidth": 4,
    "trailingComma": "es5",
    "useTabs": true
};

/* eslint-disable no-console */

function getConfig() {
    let config;

    try {
        config = require('@liferay/npm-scripts/src/config/prettier');
    }
    catch (error) {
        console.log(`info: using fallback config in ${__filename}`);

        return FALLBACK_CONFIG;
    }

    if (JSON.stringify(FALLBACK_CONFIG) !== JSON.stringify(config)) {
        console.warn(
            `warning: The fallback config in ${__filename} is out of sync ` +
            'with the one in @liferay/npm-scripts and should be updated', config
        );
    }
    return config;
}

module.exports = getConfig();
