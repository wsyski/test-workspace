/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {DisplayType} from '@clayui/alert';
import {FirstDayOfWeek} from '@clayui/date-picker';
import {FormikValues} from 'formik';

import AlertMessages from './components/AlertMessages';
import ArenaIcon, {
	ARENA_ICON_SPRITEMAP,
	ArenaIconSpriteContext,
} from './components/ArenaIcon';
import ErrorBoundary from './components/ErrorBoundary';
import LoadingIndicator from './components/LoadingIndicator';
import Permalink from './components/Permalink';
import ShowMoreList from './components/ShowMoreList';
import ShowMoreText from './components/ShowMoreText';
import ErrorFocus from './components/form/ErrorFocus';
import {
	ErrorFeedback,
	HelpMessage,
	RequiredMark,
	SelectOptions,
	getFieldFeedbackId,
} from './components/form/FormInput';
import {
	ALERT_ORIGIN_DEFAULT,
	ERRORS_PREFIX,
	HEADER_AUTHORIZATION,
	HTTP_STATUS_BAD_REQUEST,
	LOCALIZATION_PARAMS_DEFAULT
} from './constants/PortletCommonConstants';
import AlertActionsContext from './contexts/AlertActionsContext';
import CommonServicesConfigContext, {
	COMMON_SERVICES_CONFIG_DEFAULT,
} from './contexts/CommonServicesConfigContext';
import LiferayParamsContext, {
	LIFERAY_PARAMS_DEFAULT,
} from './contexts/LiferayParamsContext';
import useLiferayParams from './hooks/useLiferayParams';
import useLocalStorage from './hooks/useLocalStorage';
import useSessionStorage from './hooks/useSessionStorage';
import EventSourceCloseToken from './models/EventSourceCloseToken';
import {AxCurrencyAmount} from './models/domain/ax/AxCurrencyAmount';
import {AxExternalUrl} from './models/domain/ax/AxExternalUrl';
import {AxImageUrl} from './models/domain/ax/AxImageUrl';
import CommonServicesConfigService from './services/CommonServicesConfigService';
import AlertUtil from './utils/AlertUtil';
import EventSourceUtil from './utils/EventSourceUtil';
import I18nUtil from './utils/I18nUtil';
import LiferayUtil from './utils/LiferayUtil';
import MiscUtil from './utils/MiscUtil';
import RoutingUtil from './utils/RoutingUtil';
import ServiceUtil from './utils/ServiceUtil';
import SuspensibleResource from './utils/SuspensibleResource';

export type FieldValidations<V extends FormikValues> = {
	[K in keyof V]?: ((value: any) => string | undefined)[];
};

export type AlertConfirmType = 'ToastConfirm' | 'ModalConfirm'| 'ModalConfirmCancel';

export interface Alert {
	message: string;
	displayType: DisplayType;
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

export interface LocalizationParams {
	currencyAmount: string;
	dateFormat: string;
	firstDayOfWeek: FirstDayOfWeek;
	months: string[];
	weekdaysShort: string[];
}

export interface CommonServicesConfig {
	calendarApiEndpoint: string;
	calendarCustomerId: string;
	calendarDefaultAllowedAttendees: number;
	calendarLocationVocabularyId: number;
	calendarTargetAudienceVocabularyId: number;
	federatedSearchCustomerId: string;
	federatedSearchApiEndpoint: string;
	federatedSearchSourceConfig: any;
	googleAnalyticsMeasurementId: string;
	openingHoursApiEndpoint: string;
	openingHoursCustomerId: string;
	transactionApiEndpoint: string;
	transactionTenantId: string;
}

export {
	ARENA_ICON_SPRITEMAP,
	ALERT_ORIGIN_DEFAULT,
	COMMON_SERVICES_CONFIG_DEFAULT,
	ERRORS_PREFIX,
	LIFERAY_PARAMS_DEFAULT,
	HTTP_STATUS_BAD_REQUEST,
	HEADER_AUTHORIZATION,
	LOCALIZATION_PARAMS_DEFAULT,
	AlertActionsContext,
	AlertUtil,
	ArenaIcon,
	ArenaIconSpriteContext,
	AlertMessages,
	CommonServicesConfigService,
	CommonServicesConfigContext,
	ErrorFocus,
	ErrorBoundary,
	EventSourceUtil,
	EventSourceCloseToken,
	ErrorFeedback,
	HelpMessage,
	RequiredMark,
	SelectOptions,
	I18nUtil,
	LiferayUtil,
	LiferayParamsContext,
	LoadingIndicator,
	MiscUtil,
	Permalink,
	RoutingUtil,
	ServiceUtil,
	ShowMoreList,
	ShowMoreText,
	SuspensibleResource,
	getFieldFeedbackId,
	useLiferayParams,
	useLocalStorage,
	useSessionStorage
};

export type {AxCurrencyAmount, AxExternalUrl, AxImageUrl};
