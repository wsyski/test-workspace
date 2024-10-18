const windowLocation = window.location;
delete window.location;

Object.defineProperty(window, 'location', {
	configurable: true,
	enumerable: true,
	value: {
		...windowLocation,
		origin: 'http://localhost:6080',
	},
	writable: true,
});
