(function (exports) {
  'use strict';
  var patron={
    arenaAgencyMemberId: "30000",
    arenaUserId: "1001",
    id: "F0767453EC1641F1E04400144FFB9119",
    name: "Arena Patron",
    nick: "Arena",
    roles: ["guest", "unrestricted"],
    token: "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJlaHViQ29uc3VtZXJJZCI6MTM5NDAsImFyZW5hQWdlbmN5TWVtYmVySWQiOjMwMDAwLCJwaW4iOiIxMTExIiwiYXJlbmFVc2VySWQiOjEwMDEsInBhdHJvbklkIjoiRjA3Njc0NTNFQzE2NDFGMUUwNDQwMDE0NEZGQjkxMTkiLCJyb2xlcyI6WyJndWVzdCIsInVucmVzdHJpY3RlZCJdLCJpc3MiOiI0MDAwMCIsIm5hbWUiOiJBcmVuYSBQYXRyb24iLCJsaWJyYXJ5Q2FyZCI6IjExMTEiLCJpYXQiOjE3MDY5NjIyOTgsImJpcnRoRGF0ZSI6IjE5NTUtMDktMTMiLCJlbWFpbCI6Indvc0BheGllbGwuY29tIn0.FfffYKgCt-mG7m6Vti4sY-8qq16WPefmUyticsvWxP8"
  };
  var maxInactiveInterval = 900;
  var localApiEndpoint = "http://arena-local:16520/local-rest/api/v1";
  var federatedSearchApiEndpoint = "http://arena-federated-search:9799";
  var transactionApiEndpoint = "http://arena-transaction:9610";
  function isSignedIn() {
    return !!patron;
  }
  function getMaxInactiveInterval() {
    return maxInactiveInterval;
  }
  function getApiEndpoint(localhostApiEndpoint, proxyPathname) {
    var localhostPathname = (new URL(localhostApiEndpoint)).pathname;
    if (localhostPathname.endsWith('/')) {
      localhostPathname = localhostPathname.slice(0, -1);
    }
    var localhostPort = (new URL(localhostApiEndpoint)).port;
    var localhostProtocol = (new URL(localhostApiEndpoint)).protocol;
    var port = location.port;
    var hostname = location.hostname;
    switch (port) {
      case undefined:
      case '':
      case '80':
      case '443':
        return proxyPathname;
      case '3000':
      case '6080':
      case '8080':
      case '16519':
        return localhostProtocol === 'https' ? localhostApiEndpoint : '//' + hostname + ':' + localhostPort + localhostPathname;
      default:
        return localhostApiEndpoint;
    }
  }
  function getLocalApiEndpoint() {
    return getApiEndpoint(localApiEndpoint, '/local-rest/api/v1');
  }
  function getFederatedSearchApiEndpoint() {
    return getApiEndpoint(federatedSearchApiEndpoint, '/federated-search');
  }
  function getTransactionApiEndpoint() {
    return getApiEndpoint(transactionApiEndpoint, '/transaction');
  }
  function getPatron() {
    return patron;
  }
  function setPatron(p) {
    patron = p;
  }
  exports.isSignedIn = isSignedIn;
  exports.getMaxInactiveInterval = getMaxInactiveInterval;
  exports.getLocalApiEndpoint = getLocalApiEndpoint;
  exports.getFederatedSearchApiEndpoint = getFederatedSearchApiEndpoint;
  exports.getTransactionApiEndpoint = getTransactionApiEndpoint;
  exports.getPatron = getPatron;
  exports.setPatron = setPatron;
} (window.Arena = window.Arena || {}));
