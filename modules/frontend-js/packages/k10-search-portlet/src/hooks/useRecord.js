import {useEffect, useState} from 'react';

import RecordService from '../services/RecordService';

const useRecord = (id, baseUrl, collection) => {
	const [record, setRecord] = useState({});

	useEffect(() => {
		RecordService.getRecord(id, baseUrl, collection).then((record) => {
			setRecord(record);
		}).catch(ex => {
			console.error(ex.message());
		});
	}, [id, baseUrl, collection]);

	return record;
};

export default useRecord;
