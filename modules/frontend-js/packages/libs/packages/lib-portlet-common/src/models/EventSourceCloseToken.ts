export default class EventSourceCloseToken {
	private eventSources: EventSource[] = [];

	constructor() {}

	public add(eventSource: EventSource): void {
		this.eventSources.push(eventSource);
	}

	public close(): void {
		this.eventSources.forEach((eventSource) => eventSource.close());
		this.eventSources = [];
	}
}
