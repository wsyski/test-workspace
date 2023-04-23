import EventSourceCloseToken from '../models/EventSourceCloseToken';

export default class EventSourceUtil {
	static eventSourceFactory(
		url: string,
		eventSourceCloseToken: EventSourceCloseToken
	): EventSource {
		const eventSource = new EventSource(url);
		eventSourceCloseToken.add(eventSource);

		return eventSource;
	}
}
