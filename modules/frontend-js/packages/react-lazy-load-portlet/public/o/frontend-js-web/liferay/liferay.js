(function (exports) {
	'use strict';

	function ThemeDisplay() {
		let _languageId = 'en_US';
		let _scopeGroupId = 39847;

		function getLanguageId() {
			return _languageId;
		}

		function setLanguageId(languageId) {
			_languageId = languageId;
		}

		function getLayoutURL() {
			return '';
		}

		function getPortalURL() {
			return 'http://localhost:6080';
		}

		function isSignedIn() {
			return false;
		}

		function getPathContext() {
			return '';
		}

		function getPathThemeImages() {
			return '/o/classic-theme/images';
		}

		function getScopeGroupId() {
			return _scopeGroupId;
		}

		function setScopeGroupId(scopeGroupId) {
			_scopeGroupId = scopeGroupId;
		}

		function getLayoutRelativeURL() {
			return '/web/arena/welcome';
		}

		return {
			getLayoutURL,
			getLayoutRelativeURL,
			getLanguageId,
			setLanguageId,
			getPathContext,
			getPathThemeImages,
			getPortalURL,
			getScopeGroupId,
			isSignedIn,
			setScopeGroupId,
		};
	}

	function Browser() {
		const _userAgent = navigator.userAgent.toLowerCase();

		function isEdge() {
			if (_userAgent.indexOf('edge') >= 0) {
				return true;
			}

			return false;
		}

		function isChrome() {
			if (isEdge()) {
				return false;
			}
			if (_userAgent.indexOf('chrome') >= 0) {
				return true;
			}

			return false;
		}

		function isIe() {
			if (
				(_userAgent.indexOf('msie') >= 0 ||
					_userAgent.indexOf('trident') >= 0) &&
				!_userAgent.indexOf('opera') >= 0
			) {
				return true;
			}

			return false;
		}

		return {
			isChrome,
			isEdge,
			isIe,
		};
	}

	function Service(serviceUrl, body, callback) {
		const calendarConfig = {
			calendarApiEndpoint: 'http://127.0.1.1:9599',
			customerId: '5c41e81c85879d080306a45b',
		};
		switch (serviceUrl) {
			case '/arenacalendar.calendar/get-calendar-config':
				callback(calendarConfig);
				break;
			default:
				console.error('Invalid service url: ' + serviceUrl);
		}
	}

	function ResourceURL() {
		const _baseUrl = location.origin;
		let _resourceId;
		let _portletId;

		function getPortletName(portletId) {
			if (portletId) {
				return portletId.replace(/_INSTANCE_\w+$/g, '');
			}
		}

		/* eslint no-unused-vars: off */
		function setParameter(key, value) {}

		function setPortletId(portletId) {
			_portletId = portletId;
		}

		function setResourceId(resourceId) {
			_resourceId = resourceId;
		}

		function toString() {
			let url = _baseUrl + '/resources';
			if (_resourceId) {
				url += _resourceId;
			}
			const portletName = getPortletName(_portletId);
			if (portletName) {
				url += '-' + portletName;
			}

			return url + '.json';
		}

		return {
			setParameter,
			setResourceId,
			setPortletId,
			toString,
		};
	}

	function PortletURL() {
		function createResourceURL() {
			return new ResourceURL();
		}

		return {
			createResourceURL,
		};
	}

	function component(id, value, componentConfig) {}

	exports.component = component;
	exports.Language = {
		get(id, args) {
			return args ? id + ' ' + args.join(',') : id;
		},
	};
	exports.Browser = new Browser();
	exports.PortletURL = new PortletURL();
	exports.Service = Service;
	exports.ThemeDisplay = new ThemeDisplay();
	exports.ResourceURL = ResourceURL;
})((window.Liferay = window.Liferay || {}));
