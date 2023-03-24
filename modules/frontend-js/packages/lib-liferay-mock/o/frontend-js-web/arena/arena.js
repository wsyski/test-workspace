(function (exports) {
	'use strict';
	const patron = {
		arenaAgencyMemberId: '30000',
		arenaUserId: '1001',
		displayName: 'Arena Patron',
		id: 'F0767453EC1641F1E04400144FFB9119',
		nick: 'Arena',
		token: 'eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJlaHViQ29uc3VtZXJJZCI6MTM5NDAsImFyZW5hQWdlbmN5TWVtYmVySWQiOjMwMDAwLCJhcmVuYVVzZXJJZCI6MTAwMiwicGF0cm9uSWQiOiJGMDc2NzQ1M0VDMTY0MUYxRTA0NDAwMTQ0RkZCOTExOSIsImlzcyI6IjQwMDAwIiwibmFtZSI6IkJlbm55IEFuZGVyc3NvbiIsImxpYnJhcnlDYXJkIjoiMTExMSIsImlhdCI6MTY3ODExMzY5NSwiYmlydGhEYXRlIjoiMTk1NS0wOS0xMyIsImVtYWlsIjoiaGhuQGF4aWVsbC5jb20ifQ.0zNzHxukMfVY5VpUSeHtHPuWBiyEIuqxnYdmdOjI2sU',
	};

	const localApiEndpoint = 'http://arena-local:16520/local-rest/api/v1';

	function isSignedIn() {
		return !!patron;
	}

	function getLocalApiEndpoint() {
		const pathname = new URL(localApiEndpoint).pathname;
		const port = location.port;
		const hostname = location.hostname;
		switch (port) {
			case undefined:
			case '':
			case '80':
			case '443':
			case '3000':
			case '6080':
				return '//theduck.axiell.com:16520' + pathname;
			case '16519':
				return '//' + hostname + ':16520' + pathname;
			default:
				return localApiEndpoint;
		}
	}
	function getPatron() {
		return patron;
	}

	exports.isSignedIn = isSignedIn;
	exports.getLocalApiEndpoint = getLocalApiEndpoint;
	exports.getPatron = getPatron;
})((window.Arena = window.Arena || {}));

