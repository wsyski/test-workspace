module.exports = {
	env: {
		browser: true,
		jest: true,
		node: true,
	},
	extends: ['plugin:@liferay/react'],
	globals: {
		Liferay: 'readonly',
	},
	ignorePatterns: [
		'packages/**/__tests__',
		'packages/**/__mocks__',
		'packages/**/setupTests.js',
		'packages/**/setupTests.ts',
		'node_modules',
	],
	overrides: [
		{
			env: {
				jest: true,
			},
			files: [
				'**/__tests__/**/*.js',
				'**/test/**/*.js',
				'**/tests/**/*.js',
			],
		},
	],
	plugins: ['@liferay'],
	rules: {
		'@liferay/no-anonymous-exports': 'off',
		'@typescript-eslint/member-ordering': 'off',
		'react/jsx-fragments': 'off'
	},
};
