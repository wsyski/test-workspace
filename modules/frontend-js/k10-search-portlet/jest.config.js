module.exports = {
	collectCoverage: false,
	globals: {
		window: {},
	},

	moduleFileExtensions: ['js', 'jsx', 'json', 'node'],
	roots: ['<rootDir>/src'],
	setupFilesAfterEnv: ['<rootDir>/src/setupTests.js'],
	snapshotSerializers: ['enzyme-to-json/serializer'],
	testEnvironment: 'jsdom',
	testRegex: '(/__tests__/.*|(\\.|/)(test|spec))\\.jsx?$',
	transform: {
		'^.+\\.jsx?$': 'babel-jest',
	},
};
