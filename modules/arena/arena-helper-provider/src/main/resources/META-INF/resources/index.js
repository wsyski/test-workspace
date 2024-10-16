(function(exports) {
    "use strict";
    var PROJECT_VERSION = "1.0.0";
    var LIB_ANALYTICS_MODULE = "@react-provider$arena/lib-analytics@" + PROJECT_VERSION;
    var I18NEXT_MODULE = "react-provider$i18next@21.6.16";
    var I18NEXT_CHAINED_BACKEND_MODULE =
      "react-provider$i18next-chained-backend@3.0.2";
    var I18NEXT_LOCALSTORAGE_BACKEND_MODULE =
      "react-provider$i18next-localstorage-backend@3.1.3";
    var I18NEXT_XHR_BACKEND_MODULE = "react-provider$i18next-xhr-backend@3.2.2";
    var DEFAULT_LANGUAGE = "dev";
    var LOAD_PATH = "/o/helper-provider/locales/";
    var CACHE_EXPIRATION_TIME = 60 * 60 * 1000;

    function getLocale() {
      let locale = Liferay.ThemeDisplay.getLanguageId();
      if (locale === "nn_NO") {
        locale = "nb_NO";
      }

      return locale;
    }

    function withTranslation(callback) {
      Liferay.Loader.require(I18NEXT_MODULE, (i18next) => {
        Liferay.Loader.require(
          I18NEXT_CHAINED_BACKEND_MODULE,
          (i18nextChainedBackend) => {
            Liferay.Loader.require(
              I18NEXT_LOCALSTORAGE_BACKEND_MODULE,
              (i18nextLocalstorageBackend) => {
                Liferay.Loader.require(
                  I18NEXT_XHR_BACKEND_MODULE,
                  (i18nextBackend) => {
                    const i18nInstance = i18next.createInstance();

                    i18nInstance.use(i18nextChainedBackend).init(
                      {
                        backend: {
                          backendOptions: [
                            {
                              defaultVersion: PROJECT_VERSION,
                              expirationTime: CACHE_EXPIRATION_TIME,
                              prefix: "i18next" + LOAD_PATH.replaceAll("/", "_")
                            },
                            {
                              crossDomain: false,
                              loadPath: (languages) => {
                                return (
                                  LOAD_PATH +
                                  (languages.length === 1 &&
                                  languages[0] === DEFAULT_LANGUAGE
                                    ? "{{ns}}.json"
                                    : "{{lng}}/{{ns}}.json")
                                );
                              },
                              version: PROJECT_VERSION
                            }
                          ],
                          backends: [
                            i18nextLocalstorageBackend,
                            i18nextBackend
                          ]
                        },
                        debug: false,
                        defaultNS: "translation",
                        fallbackLng: DEFAULT_LANGUAGE,
                        lng: getLocale(),
                        ns: ["translation"]
                      },
                      (error, t) => {
                        callback(t);
                      }
                    );
                  }
                );
              }
            );
          }
        );
      });
    }

    function withAnalytics(callback) {
      Liferay.Loader.require(LIB_ANALYTICS_MODULE, (analyticsModule) => {
        callback(analyticsModule);
      });
    }

    exports.withAnalytics = withAnalytics;
    exports.getLocale = getLocale;
    exports.withTranslation = withTranslation;
  }

)((window.ArenaHelper = window.ArenaHelper || {}));

// console.log('Bootstrapped helper-provider');
