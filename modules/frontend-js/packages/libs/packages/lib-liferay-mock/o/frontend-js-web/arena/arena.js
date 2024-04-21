(function(exports) {
	"use strict";

	/* theduck.axiell.com arenaMemberId: 30000 */
	const patron = {
		arenaAgencyMemberId: "30000",
		arenaUserId: "1001",
		id: "F0767453EC1641F1E04400144FFB9119",
		name: "Arena Patron",
		nick: "Arena",
		roles: ["guest", "unrestricted"],
		token: "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJlaHViQ29uc3VtZXJJZCI6MTM5NDAsImFyZW5hQWdlbmN5TWVtYmVySWQiOjMwMDAwLCJwaW4iOiIxMTExIiwiYXJlbmFVc2VySWQiOjEwMDEsInBhdHJvbklkIjoiRjA3Njc0NTNFQzE2NDFGMUUwNDQwMDE0NEZGQjkxMTkiLCJyb2xlcyI6WyJndWVzdCIsInVucmVzdHJpY3RlZCJdLCJpc3MiOiI0MDAwMCIsIm5hbWUiOiJBcmVuYSBQYXRyb24iLCJsaWJyYXJ5Q2FyZCI6IjExMTEiLCJpYXQiOjE3MDY5NjIyOTgsImJpcnRoRGF0ZSI6IjE5NTUtMDktMTMiLCJlbWFpbCI6Indvc0BheGllbGwuY29tIn0.FfffYKgCt-mG7m6Vti4sY-8qq16WPefmUyticsvWxP8"
	};

	/* theduck.axiell.com arenaMemberId: 30000 */
	/*
    const patron = {
      arenaAgencyMemberId: "30000",
      arenaUserId: "1001",
      id: "F0767453EC1641F1E04400144FFB9119",
      name: "Arena Patron",
      nick: "Arena",
      roles: ["guest", "restricted"],
      token: "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJlaHViQ29uc3VtZXJJZCI6MTM5NDAsImFyZW5hQWdlbmN5TWVtYmVySWQiOjMwMDAwLCJwaW4iOiIxMTExIiwiYXJlbmFVc2VySWQiOjEwMDEsInBhdHJvbklkIjoiRjA3Njc0NTNFQzE2NDFGMUUwNDQwMDE0NEZGQjkxMTkiLCJyb2xlcyI6WyJndWVzdCIsInJlc3RyaWN0ZWQiXSwiaXNzIjoiNDAwMDAiLCJuYW1lIjoiQXJlbmEgUGF0cm9uIiwibGlicmFyeUNhcmQiOiIxMTExIiwiaWF0IjoxNzA2OTYyMzcyLCJiaXJ0aERhdGUiOiIxOTU1LTA5LTEzIiwiZW1haWwiOiJ3b3NAYXhpZWxsLmNvbSJ9.QyQohsSAX23C-kslMVth7xLU8vYF0_fk4DAjH0-3uh0"
    };
    */

	/* theduck.axiell.com arenaMemberId: 30000 */
	/*
    const patron = {
      arenaAgencyMemberId: "30000",
      id: "patronId",
      name: "Arena Patron",
      roles: ["unrestricted"],
      token: "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJlaHViQ29uc3VtZXJJZCI6MTM5NDAsImFyZW5hQWdlbmN5TWVtYmVySWQiOjMwMDAwLCJwaW4iOiIxMTExIiwiYXJlbmFVc2VySWQiOjEwMDEsInBhdHJvbklkIjoiRjA3Njc0NTNFQzE2NDFGMUUwNDQwMDE0NEZGQjkxMTkiLCJyb2xlcyI6WyJ1bnJlc3RyaWN0ZWQiXSwiaXNzIjoiNDAwMDAiLCJuYW1lIjoiQXJlbmEgUGF0cm9uIiwibGlicmFyeUNhcmQiOiIxMTExIiwiaWF0IjoxNzA4MDg3MjY4LCJiaXJ0aERhdGUiOiIxOTU1LTA5LTEzIiwiZW1haWwiOiJ3b3NAYXhpZWxsLmNvbSJ9.GY1A64gxalw4qoD0LhZ-QJ_4gIUVCcJdYkqTb7foDjg"
    };
     */

	/* theduck.axiell.com arenaMemberId: 30000 */
	/*
    const patron = {
      arenaAgencyMemberId: "30000",
      id: "patronId",
      name: "Arena Patron",
      roles: ["restricted"],
      token: "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJlaHViQ29uc3VtZXJJZCI6MTM5NDAsImFyZW5hQWdlbmN5TWVtYmVySWQiOjMwMDAwLCJhcmVuYVVzZXJJZCI6MTAwMSwicGF0cm9uSWQiOiJGMDc2NzQ1M0VDMTY0MUYxRTA0NDAwMTQ0RkZCOTExOSIsInJvbGVzIjpbInJlc3RyaWN0ZWQiXSwiaXNzIjoiNDAwMDAiLCJsaWJyYXJ5Q2FyZCI6IjExMTEiLCJiaXJ0aERhdGUiOiIxOTU1LTA5LTEzIiwicGluIjoiMTExMSIsIm5hbWUiOiJBcmVuYSBQYXRyb24iLCJleHAiOjE3MDgwMDE1NTMsImlhdCI6MTcwODAwMTU1MiwiZW1haWwiOiJ3b3NAYXhpZWxsLmNvbSJ9.zuvRtsO8vqF1DEqodMpQmjv66ELmvUtqmC--nboXAgA"
    };
     */

	const localApiEndpoint = "http://arena-local:16520/local-rest/api/v1";

	function isSignedIn() {
		return !!patron;
	}

	function getLocalApiEndpoint() {
		const pathname = new URL(localApiEndpoint).pathname;
		const port = location.port;
		const hostname = location.hostname;
		switch (port) {
			case undefined:
			case "":
			case "80":
			case "443":
				return "//" + hostname + pathname;
			case "3000":
			case "6080":
				return "//theduck.axiell.com:16520" + pathname;
			case "16519":
				return "//" + hostname + ":16520" + pathname;
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

