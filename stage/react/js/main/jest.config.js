module.exports = {
	collectCoverage: false,
	globals: {
		window: {},
	},

	moduleFileExtensions: ['ts', 'js', 'jsx', 'json', 'node'],
	roots: ['<rootDir>/src'],
	setupFiles: ['./jest.polyfills.js'],
	setupFilesAfterEnv: ['<rootDir>/src/setupTests.js'],
	snapshotSerializers: ['enzyme-to-json/serializer'],
	testEnvironment: 'jsdom',
	testRegex: '(/__tests__/.*|(\\.|/)(test|spec))\\.jsx?$',
	transform: {
		'^.+\\.tsx?$': ['ts-jest', {tsconfig: '<rootDir>/tsconfig.json'}]
	},
};
