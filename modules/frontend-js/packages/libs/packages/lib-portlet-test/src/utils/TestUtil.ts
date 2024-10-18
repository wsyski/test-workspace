import {AxiosResponse} from 'axios';
import {ReactWrapper, ShallowWrapper} from 'enzyme';

export default class TestUtil {
	static findDeepByTestAttr(
		component: ReactWrapper,
		attr: string
	): ReactWrapper {
		return component.find(`[data-test-id='${attr}']`);
	}

	static findShallowByTestAttr(
		component: ShallowWrapper,
		attr: string
	): ShallowWrapper {
		return component.find(`[data-test-id='${attr}']`);
	}

	static getAxiosResponse<T>(data: T): AxiosResponse<T> {
		const axiosResponse: AxiosResponse = {
			config: {},
			data,
			headers: {},
			status: 200,
			statusText: 'OK',
		};

		return axiosResponse;
	}
}
