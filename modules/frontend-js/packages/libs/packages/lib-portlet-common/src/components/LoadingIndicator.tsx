import ClayLoadingIndicator from '@clayui/loading-indicator';
import React from 'react';

export interface Props {
	isLoading: boolean;
}

const LoadingIndicator: React.FC<Props> = ({isLoading}: Props) => {
	return (
		<React.Fragment>
			{' '}

			{isLoading && <ClayLoadingIndicator />}{' '}
		</React.Fragment>
	);
};

export default LoadingIndicator;
