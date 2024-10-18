module.exports = {
	collectCoverage: false,
	globals: {
		window: {},
	},

	moduleFileExtensions: ['js', 'jsx', 'json', 'node'],
	roots: ['<rootDir>/src'],
	setupFiles: ['./jest.polyfills.js'],
	setupFilesAfterEnv: ['<rootDir>/src/setupTests.js'],
	snapshotSerializers: ['enzyme-to-json/serializer'],
	testEnvironment: 'jsdom',
	testRegex: '(/__tests__/.*|(\\.|/)(test|spec))\\.jsx?$',
	transform: {
		"^.+\\.(js|jsx)$": ["babel-jest", { "rootMode": "upward" }],
	}
};
