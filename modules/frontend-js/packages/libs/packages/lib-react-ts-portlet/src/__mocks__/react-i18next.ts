module.exports = {
	useTranslation: () => {
		return {
			i18n: {
				changeLanguage: () => new Promise(() => {}),
			},
			t: (str: string) => str,
		};
	},
	withTranslation: () => (Component: React.FC) => Component,
};

export {};
