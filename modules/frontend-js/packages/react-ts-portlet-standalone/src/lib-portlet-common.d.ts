export declare enum FirstDayOfWeek {
    Sunday = 0,
    Monday = 1,
    Tuesday = 2,
    Wednesday = 3,
    Thursday = 4,
    Friday = 5,
    Saturday = 6
}

export interface Configuration {
    [key: string]: string | string[];
}

export interface LocalizationParams {
    currencyAmount: string;
    dateFormat: string;
    firstDayOfWeek: FirstDayOfWeek;
    months: string[];
    weekdaysShort: string[];
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

export interface AppContainerProps {
    liferayParams: LiferayParams;
}
