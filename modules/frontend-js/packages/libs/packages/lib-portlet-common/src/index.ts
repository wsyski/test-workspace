import { FirstDayOfWeek } from "@clayui/date-picker";
import { Status } from "@clayui/modal/lib/types";
import { FormikValues } from "formik";

import AlertMessages from "./components/AlertMessages";
import ArenaIcon, { ARENA_ICON_SPRITEMAP, ArenaIconSpriteContext } from "./components/ArenaIcon";
import ErrorBoundary from "./components/ErrorBoundary";
import FutureClayDatePicker from "./components/FutureClayDatePicker";
import Permalink from "./components/Permalink";
import ShowMoreList from "./components/ShowMoreList";
import ShowMoreText from "./components/ShowMoreText";
import UrlPermalink from "./components/UrlPermalink";
import WithLiferayParams from "./components/WithLiferayParams";
import EnabledDatesClayDatePicker, { EnabledDatesClayDatePickerProps } from "./components/clay-date-picker/EnabledDatesClayDatePicker";
import ErrorFocus from "./components/form/ErrorFocus";
import { ErrorFeedback, HelpMessage, RequiredMark, SelectOptions, getFieldFeedbackId } from "./components/form/FormInput";
import { ALERT_ORIGIN_DEFAULT, ERRORS_PREFIX, HEADER_AUTHORIZATION, HTTP_STATUS_BAD_REQUEST, LOCALIZATION_PARAMS_DEFAULT } from "./constants/PortletCommonConstants";
import AlertActionsContext from "./contexts/AlertActionsContext";
import CommonServicesConfigContext, { COMMON_SERVICES_CONFIG_DEFAULT } from "./contexts/CommonServicesConfigContext";
import LiferayParamsContext, { LIFERAY_PARAMS_DEFAULT } from "./contexts/LiferayParamsContext";
import useLiferayParams from "./hooks/useLiferayParams";
import useLocalStorage from "./hooks/useLocalStorage";
import useSessionStorage from "./hooks/useSessionStorage";
import EventSourceCloseToken from "./models/EventSourceCloseToken";
import { AxChronoUnit } from "./models/domain/ax/AxChronoUnit";
import { AxCurrencyAmount } from "./models/domain/ax/AxCurrencyAmount";
import { AxDateModel } from "./models/domain/ax/AxDateModel";
import { AxDateQualifier } from "./models/domain/ax/AxDateQualifier";
import AxExtendedDate from "./models/domain/ax/AxExtendedDate";
import { AxExternalUrl } from "./models/domain/ax/AxExternalUrl";
import { AxImageUrl } from "./models/domain/ax/AxImageUrl";
import CommonServicesConfigService from "./services/CommonServicesConfigService";
import AlertUtil from "./utils/AlertUtil";
import EventSourceUtil from "./utils/EventSourceUtil";
import I18nUtil from "./utils/I18nUtil";
import LiferayUtil from "./utils/LiferayUtil";
import MiscUtil from "./utils/MiscUtil";
import RoutingUtil from "./utils/RoutingUtil";
import ServiceUtil from "./utils/ServiceUtil";
import SuspensibleResource from "./utils/SuspensibleResource";

export type FieldValidations<V extends FormikValues> = {
  [K in keyof V]?: ((value: any) => string | undefined)[];
};

export type AlertConfirmType = "ToastConfirm" | "ModalConfirm" | "ModalConfirmCancel";

export interface Alert {
  message: string;
  status: Status;
  origin: string;
  confirmType: AlertConfirmType;
  title?: string;
}

export interface LiferayParamsConfiguration {
  portletInstance: Configuration;
  system: Configuration;
}

export interface LiferayParams {
  contextPath: string;
  portletElementId: string;
  portletNamespace: string;
  configuration: LiferayParamsConfiguration;
}

export interface AlertActions {
  addAlert: (alert: Alert) => void;
  deleteAlert: (alert: Alert) => void;
  error: (message: string) => void;
  warning: (message: string) => void;
  info: (message: string) => void;
  success: (message: string) => void;
}

export interface AppContainerProps {
  liferayParams: LiferayParams;
}

export interface Configuration {
  [key: string]: string | string[];
}

export type MappedPropertyType<T, F extends keyof T, U> = {
  [K in keyof T]: K extends F ? U : T[K];
};

export interface ExtendedLiferayParams extends LiferayParams {
  getSystemValueAsArray: (key: string) => string[];
  getSystemValueAsBoolean: (key: string) => boolean;
  getSystemValueAsNumber: (key: string) => number;
  getSystemValueAsString: (key: string) => string;
  getInstanceValueAsArray: (key: string) => string[];
  getInstanceValueAsBoolean: (key: string) => boolean;
  getInstanceValueAsNumber: (key: string) => number;
  getInstanceValueAsString: (key: string) => string;
}

export interface HoursMinutes {
  hours: string;
  minutes: string;
}

export interface LocalizationParams {
  currencyAmount: string;
  dateFormat: Record<AxChronoUnit, string>;
  firstDayOfWeek: FirstDayOfWeek;
  months: string[];
  timeFormat: string;
  weekdaysShort: string[];
}

export interface CommonServicesConfig {
  calendarApiEndpoint: string;
  calendarCustomerId: string;
  calendarDefaultAllowedAttendees: number;
  calendarLocationVocabularyId: number;
  calendarTargetAudienceVocabularyId: number;
  coversApiEndpoint: string;
  coversCustomerId: string;
  federatedSearchCustomerAlias: string;
  federatedSearchSourceConfig: any;
  googleAnalyticsMeasurementId: string;
  openingHoursApiEndpoint: string;
  openingHoursCustomerId: string;
  transactionTenantId: string;
}

export {
  ALERT_ORIGIN_DEFAULT,
  ARENA_ICON_SPRITEMAP,
  AlertActionsContext,
  AlertMessages,
  AlertUtil,
  ArenaIcon,
  ArenaIconSpriteContext,
  AxDateModel,
  AxDateQualifier,
  AxChronoUnit,
  COMMON_SERVICES_CONFIG_DEFAULT,
  CommonServicesConfigContext,
  CommonServicesConfigService,
  EnabledDatesClayDatePicker,
  FutureClayDatePicker,
  ERRORS_PREFIX,
  ErrorBoundary,
  ErrorFeedback,
  ErrorFocus,
  EventSourceCloseToken,
  EventSourceUtil,
  HEADER_AUTHORIZATION,
  HTTP_STATUS_BAD_REQUEST,
  HelpMessage,
  I18nUtil,
  LIFERAY_PARAMS_DEFAULT,
  LOCALIZATION_PARAMS_DEFAULT,
  LiferayParamsContext,
  LiferayUtil,
  MiscUtil,
  Permalink,
  RequiredMark,
  RoutingUtil,
  SelectOptions,
  ServiceUtil,
  ShowMoreList,
  ShowMoreText,
  SuspensibleResource,
  UrlPermalink,
  WithLiferayParams,
  getFieldFeedbackId,
  useLiferayParams,
  useLocalStorage,
  useSessionStorage
};

export type { AxCurrencyAmount, AxExternalUrl, AxImageUrl, AxExtendedDate, EnabledDatesClayDatePickerProps };
