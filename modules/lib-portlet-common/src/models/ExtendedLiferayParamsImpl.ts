import {
	ExtendedLiferayParams,
	LiferayParams,
	LiferayParamsConfiguration,
} from '../index';

export const getBoolean = (value: string): boolean => {
	return value === 'true';
};

export default class ExtendedLiferayParamsImpl
	implements ExtendedLiferayParams
{
	contextPath: string;
	portletElementId: string;
	portletNamespace: string;
	configuration: LiferayParamsConfiguration;

	constructor(liferayParams: LiferayParams) {
		this.contextPath = liferayParams.contextPath;
		this.portletElementId = liferayParams.portletElementId;
		this.portletNamespace = liferayParams.portletNamespace;
		this.configuration = liferayParams.configuration;
	}

	getSystemValueAsString = (key: string): string => {
		return this.configuration.system[key] as string;
	};

	getSystemValueAsNumber = (key: string): number => {
		return Number(this.getSystemValueAsString(key));
	};

	getSystemValueAsBoolean = (key: string): boolean => {
		return getBoolean(this.getInstanceValueAsString(key));
	};

	getSystemValueAsArray = (key: string): string[] => {
		return this.configuration.system[key] as string[];
	};

	getInstanceValueAsString = (key: string): string => {
		return this.configuration.portletInstance[key] as string;
	};

	getInstanceValueAsNumber = (key: string): number => {
		return Number(this.getInstanceValueAsString(key));
	};

	getInstanceValueAsBoolean = (key: string): boolean => {
		return getBoolean(this.getInstanceValueAsString(key));
	};

	getInstanceValueAsArray = (key: string): string[] => {
		return this.configuration.portletInstance[key] as string[];
	};

	getContextPath = (): string => {
		return this.contextPath;
	};
}
