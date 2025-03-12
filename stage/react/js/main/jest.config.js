const transformIgnoreModules = ['photoswipe', 'react-photoswipe-gallery'].join('|');

module.exports = {
	collectCoverage: false,
	globals: {
		window: {},
	},

	moduleFileExtensions: ['js', 'jsx', 'json', 'node'],
	roots: ['<rootDir>/src'],
	setupFiles: ['./jest.polyfills.js'],
	setupFilesAfterEnv: ['<rootDir>/src/setupTests.js'],
	testEnvironment: 'jsdom',
	testRegex: '(/__tests__/.*|(\\.|/)(test|spec))\\.jsx?$',
	transform: {
		"^.+\\.(js|jsx)$": ["babel-jest", { rootMode: "upward" }],
	},
	transformIgnorePatterns: [`/node_modules/(?!${transformIgnoreModules})`]
};
