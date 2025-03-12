import {AxiosResponse} from 'axios';

export default class TestUtil {

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
