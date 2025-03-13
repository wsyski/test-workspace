import { format, parse, parseISO } from "date-fns";
import i18next, { i18n } from "i18next";
import ChainedBackend from "i18next-chained-backend";
import LocalStorageBackend from "i18next-localstorage-backend";
import Backend from "i18next-xhr-backend";
import { initReactI18next } from "react-i18next";

import { LOCALIZATION_PARAMS_DEFAULT, PROJECT_VERSION } from "../constants/PortletCommonConstants";
import { LocalizationParams } from "../index";
import { AxChronoUnit } from "../models/domain/ax/AxChronoUnit";
import { AxCurrencyAmount } from "../models/domain/ax/AxCurrencyAmount";
import LiferayUtil from "./LiferayUtil";
import MiscUtil from "./MiscUtil";

const DEFAULT_LANGUAGE = "dev";
const CACHE_EXPIRATION_TIME = 60 * 60 * 1000;

export default class I18nUtil {
  static init(contextPath: string, customTranslation?: string): i18n {
    const i18nInstance = i18next.createInstance();
    i18nInstance
      .use(ChainedBackend)
      .use(initReactI18next)
      .init({
        backend: {
          backendOptions: [
            {
              defaultVersion: PROJECT_VERSION,
              expirationTime: CACHE_EXPIRATION_TIME,
              prefix:
                "i18next" +
                contextPath.replaceAll("/", "_") +
                "_"
            },
            {
              crossDomain: false,
              loadPath: (languages: string[]) => {
                const loadPath = `${contextPath}/locales/` +
                  (languages.length === 1 &&
                  languages[0] === DEFAULT_LANGUAGE
                    ? "{{ns}}.json"
                    : (customTranslation ? customTranslation + ".json" : "{{lng}}/{{ns}}.json"));

                return loadPath;
              },
              version: PROJECT_VERSION
            }
          ],
          backends: [LocalStorageBackend, Backend]
        },
        debug: false,
        defaultNS: "translation",
        fallbackLng: DEFAULT_LANGUAGE,
        keySeparator: ".",
        lng: LiferayUtil.getLocale(),
        ns: ["translation"],
        nsSeparator: "|"
      });

    return i18nInstance;
  }

  static getLocalizationParams(): LocalizationParams {
    const locale = LiferayUtil.getLocale();

    switch (locale) {
      case "sv_SE":
        return {
          currencyAmount: "${amount} ${currency}",
          dateFormat: {
            DAYS: "yyyy-MM-dd",
            MONTHS: "yyyy-MM",
            SECONDS: "yyyy-MM-dd HH:mm",
            YEARS: "yyyy"
          },
          firstDayOfWeek: 1,
          months: [
            "januari",
            "february",
            "march",
            "april",
            "maj",
            "juni",
            "juli",
            "augusti",
            "september",
            "oktober",
            "november",
            "december"
          ],
          weekdaysShort: ["S", "M", "T", "O", "T", "F", "L"]
        } as LocalizationParams;

      case "en_US":
        return {
          currencyAmount: "${currency} ${amount}",
          dateFormat: {
            DAYS: "MM/dd/yyyy",
            MONTHS: "MM/yyyy",
            SECONDS: "MM/dd/yyyy HH:mm",
            YEARS: "yyyy"
          },
          firstDayOfWeek: 0,
          months: LOCALIZATION_PARAMS_DEFAULT.months,
          weekdaysShort: ["S", "M", "T", "W", "T", "F", "S"]
        } as LocalizationParams;

      case "en_NZ":
      case "en_GB":
        return {
          currencyAmount: "${amount} ${currency}",
          dateFormat: {
            DAYS: "dd/MM/yyyy",
            MONTHS: "MM/yyyy",
            SECONDS: "dd/MM/yyyy HH:mm",
            YEARS: "yyyy"
          },
          firstDayOfWeek: 1,
          months: LOCALIZATION_PARAMS_DEFAULT.months,
          weekdaysShort: ["S", "M", "T", "W", "T", "F", "S"]
        } as LocalizationParams;
      case "de_DE":
        return {
          currencyAmount: "${amount} ${currency}",
          dateFormat: {
            DAYS: "dd.MM.yyyy",
            MONTHS: "MM.yyyy",
            SECONDS: "dd.MM.yyyy HH:mm",
            YEARS: "yyyy"
          },
          firstDayOfWeek: 1,
          months: [
            "Januar",
            "Februar",
            "März",
            "April",
            "Mai",
            "Juni",
            "Juli",
            "August",
            "September",
            "Oktober",
            "November",
            "Dezember"
          ],
          weekdaysShort: ["S", "M", "D", "M", "D", "F", "S"]
        } as LocalizationParams;
      case "fi_FI":
        return {
          currencyAmount: "${amount} ${currency}",
          dateFormat: {
            DAYS: "dd.MM.yyyy",
            MONTHS: "MM.yyyy",
            SECONDS: "dd.MM.yyyy HH:mm",
            YEARS: "yyyy"
          },
          firstDayOfWeek: 1,
          months: [
            "tammikuu",
            "helmikuu",
            "Maaliskuu",
            "huhtikuu",
            "saattaa",
            "Kesäkuu",
            "heinäkuu",
            "elokuu",
            "syyskuu",
            "lokakuu",
            "marraskuu",
            "joulukuu"
          ],
          weekdaysShort: ["S", "M", "T", "K", "T", "P", "L"]
        } as LocalizationParams;
      case "nb_NO":
        return {
          currencyAmount: "${amount} ${currency}",
          dateFormat: {
            DAYS: "dd.MM.yyyy",
            MONTHS: "MM.yyyy",
            SECONDS: "dd.MM.yyyy HH:mm",
            YEARS: "yyyy"
          },
          firstDayOfWeek: 1,
          months: [
            "januar",
            "februar",
            "mars",
            "april",
            "Kan",
            "juni",
            "juli",
            "august",
            "september",
            "oktober",
            "november",
            "desember"
          ],
          weekdaysShort: ["S", "M", "T", "O", "T", "F", "L"]
        } as LocalizationParams;
      case "fr_FR":
        return {
          currencyAmount: "${amount} ${currency}",
          dateFormat: {
            DAYS: "dd/MM/yyyy",
            MONTHS: "MM/yyyy",
            SECONDS: "dd/MM/yyyy HH:mm",
            YEARS: "yyyy"
          },
          firstDayOfWeek: 1,
          months: [
            "janvier",
            "février",
            "mars",
            "avril",
            "Peut",
            "juin",
            "juillet",
            "août",
            "septembre",
            "octobre",
            "novembre",
            "décembre"
          ],
          weekdaysShort: ["D", "L", "M", "M", "J", "V", "S"]
        } as LocalizationParams;
      case "ru_FI":
        return {
          currencyAmount: "${amount} ${currency}",
          dateFormat: {
            DAYS: "dd.MM.yyyy",
            MONTHS: "MM.yyyy",
            SECONDS: "dd.MM.yyyy HH:mm",
            YEARS: "yyyy"
          },
          firstDayOfWeek: 1,
          months: [
            "Январь",
            "Февраль",
            "Март",
            "Апрель",
            "Май",
            "Июнь",
            "Июль",
            "Август",
            "Сентябрь",
            "Октябрь",
            "Ноябрь",
            "Декабрь"
          ],
          weekdaysShort: ["Вс", "Пн", "Вт", "Ср", "Чт", "Пт", "Сб"]
        } as LocalizationParams;
      default:
        return LOCALIZATION_PARAMS_DEFAULT;
    }
  }

  static date2String(date: Date | undefined, chronoUnit: AxChronoUnit = AxChronoUnit.DAYS): string | undefined {
    if (date) {
      const localizationParams = I18nUtil.getLocalizationParams();
      try {
        return format(date, localizationParams.dateFormat[chronoUnit]);
      } catch (ex) {
        console.error("Invalid date: " + date);

        return undefined;
      }
    }
  }

  static string2Date(s: string | undefined, chronoUnit: AxChronoUnit = AxChronoUnit.DAYS): Date | undefined {
    if (s) {
      const localizationParams = I18nUtil.getLocalizationParams();
      const now = new Date();

      return new Date(parse(s, localizationParams.dateFormat[chronoUnit], now).getTime() + 43200000);
    }
  }

  static currencyAmount2String(currencyAmount: AxCurrencyAmount) {
    const localizationParams = I18nUtil.getLocalizationParams();

    return MiscUtil.template(
      localizationParams.currencyAmount,
      currencyAmount
    );
  }

  static date2IsoString(date: Date | undefined): string | undefined {
    if (date) {
      const newDate = new Date(date.getTime());
      newDate.setUTCHours(12);

      return newDate.toISOString();
    }
  }

  static dateTime2IsoString(date: Date | undefined): string | undefined {
    if (date) {
      return date.toISOString();
    }
  }

  static isoString2Date(iso: string | undefined): Date | undefined {
    if (iso) {
      return parseISO(iso);
    }
  }

  static isoDateString2DateString(s: string | undefined): string | undefined {
    if (s) {
      try {
        const date = I18nUtil.isoString2Date(s);

        return I18nUtil.date2String(date);
      } catch (ex) {
        console.error("Invalid iso date: " + s);

        return undefined;
      }
    }
  }

  static dateString2isoDateString(s: string | undefined): string | undefined {
    if (s) {
      try {
        const date = I18nUtil.string2Date(s);

        return I18nUtil.date2IsoString(date);
      } catch (ex) {
        console.error("Invalid date: " + s);

        return undefined;
      }
    }
  }
}
