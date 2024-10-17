import axios from 'axios';

export const HIT_COUNT_MAX = 99999;

const getFilterQuery = (collections) => {
	if (Array.isArray(collections) && !!collections.length) {
		return `collection:(${collections
			.map((collection) => '"' + collection + '"')
			.join(' OR ')})`;
	} else {
		return '';
	}
};

class RecordService {
	static async searchRecords(
		q,
		baseUrl,
		collections,
		pageSize,
		start,
		fields
	) {
		let url = `${baseUrl}/select?q=${encodeURIComponent(
			q
		)}&start=${start}&rows=${pageSize}`;
		const filterQuery = getFilterQuery(collections);
		if (filterQuery) {
			url += `&fq=${encodeURIComponent(filterQuery)}`;
		}
		if (fields) {
			url += `&fl=${encodeURIComponent(fields)}`;
		}
		const response = await axios.get(url);
		if (response.status !== 200) {
			console.error(
				`get: ${url} status: ${response.status} statusText: ${response.statusText}`
			);
			throw Error(response.statusText);
		}

		return {
			hitCount: response.data.response.numFound,
			records: response.data.response.docs,
		};
	}

	static async getRecord(id, baseUrl, collections) {
		const response = await RecordService.searchRecords(
			`id:${encodeURIComponent(id)}`,
			baseUrl,
			collections,
			1,
			0
		);

		return response.records && !!response.records.length
			? response.records[0]
			: undefined;
	}
}

export default RecordService;
