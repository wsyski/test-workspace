module.exports = {
	collectCoverage: false,
	globals: {
		'ts-jest': {
			tsconfig: '<rootDir>/tsconfig.json',
		},
		'window': {},
	},

	moduleFileExtensions: ['ts', 'tsx', 'js', 'jsx', 'json', 'node'],
	roots: ['<rootDir>/src'],
	setupFilesAfterEnv: ['<rootDir>/src/setupTests.ts'],
	snapshotSerializers: ['enzyme-to-json/serializer'],
	testEnvironment: 'jsdom',
	testRegex: '(/__tests__/.*|(\\.|/)(test|spec))\\.tsx?$',
	transform: {
		'^.+\\.tsx?$': 'ts-jest',
	},
};
