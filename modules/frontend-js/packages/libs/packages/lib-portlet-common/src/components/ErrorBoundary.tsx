import React, {Component, ComponentType, ErrorInfo, ReactNode} from 'react';
import {WithTranslation, withTranslation} from 'react-i18next';

import AlertActionsContext from '../contexts/AlertActionsContext';

export interface ErrorBoundaryProps {
	children: ReactNode;
}

export interface State {
	hasError: boolean;
}

class ErrorBoundaryWithT extends Component<ErrorBoundaryProps & WithTranslation, State> {
	static contextType = AlertActionsContext;
	context!: React.ContextType<typeof AlertActionsContext>;

	public state: State = {
		hasError: false,
	};

	public static getDerivedStateFromError(_: Error): State {
		// Update state so the next render will show the fallback UI.

		return {hasError: true};
	}

	public componentDidCatch(error: Error, errorInfo: ErrorInfo) {
		console.error('Uncaught error:', error, errorInfo);
		if (this.context.current !== null) {
			this.context.current.error(error.message);
		}
	}

	public render() {
		const {t} = this.props;
		if (this.state.hasError) {
			return <h2>{t('txtInternalError')}</h2>;
		}

		return this.props.children;
	}
}

export default withTranslation()(ErrorBoundaryWithT) as ComponentType<ErrorBoundaryProps>;
