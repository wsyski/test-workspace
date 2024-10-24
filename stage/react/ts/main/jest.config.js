const transformIgnoreModules = ['photoswipe', 'react-photoswipe-gallery'].join('|');

module.exports = {
	collectCoverage: false,
	globals: {
		window: {},
	},

	moduleFileExtensions: ['ts', 'tsx', 'js', 'jsx', 'json', 'node'],
	roots: ['<rootDir>/src'],
	setupFiles: ['./jest.polyfills.js'],
	setupFilesAfterEnv: ['<rootDir>/src/setupTests.ts'],
	snapshotSerializers: ['enzyme-to-json/serializer'],
	testEnvironment: 'jsdom',
	testRegex: '(/__tests__/.*|(\\.|/)(test|spec))\\.tsx?$',
	transform: {
		'^.+\\.[tj]sx?$': ['ts-jest', {tsconfig: '<rootDir>/tsconfig.json'}]
	},
	transformIgnorePatterns: [`/node_modules/(?!${transformIgnoreModules})`]
};
