const transformIgnoreModules = ['photoswipe', 'react-photoswipe-gallery'].join('|');

module.exports = {
	preset: 'ts-jest',
	testEnvironment: 'jest-environment-jsdom',
	collectCoverage: false,
	globals: {
		Liferay: {},
		window: {},
	},

	moduleFileExtensions: ['ts', 'tsx', 'js', 'jsx', 'json', 'node'],
	roots: ['<rootDir>/src'],
	setupFiles: ['./jest.polyfills.js'],
	setupFilesAfterEnv: ['<rootDir>/src/setupTests.ts'],
	testRegex: '(/__tests__/.*|(\\.|/)(test|spec))\\.tsx?$',
	transform: {
		'^.+\\.[tj]sx?$': ['ts-jest', {tsconfig: '<rootDir>/tsconfig.json'}]
	},
	transformIgnorePatterns: [`/node_modules/(?!${transformIgnoreModules})`]
};
