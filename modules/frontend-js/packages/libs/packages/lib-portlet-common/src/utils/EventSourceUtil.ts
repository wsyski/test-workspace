import { EventSourcePolyfill, EventSourcePolyfillInit } from "event-source-polyfill";

import EventSourceCloseToken from '../models/EventSourceCloseToken';

export default class EventSourceUtil {
	static eventSourceFactory(
		url: string,
		eventSourceCloseToken: EventSourceCloseToken,
		options?: EventSourcePolyfillInit
	): EventSource {
		const eventSource = new EventSourcePolyfill(url, options);
		eventSourceCloseToken.add(eventSource);

		return eventSource;
	}
}
