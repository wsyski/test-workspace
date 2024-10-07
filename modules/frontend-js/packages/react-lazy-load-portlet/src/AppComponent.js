import React, { Suspense } from "react";
import styled from "styled-components";

import ErrorBoundary from "./ErrorBoundary";
import LazyCalendar from "./LazyCalendar";
import Loader from "./Loader";

const Header = styled.h1`
  position: absolute;
  width: 100%;
  margin: 100px auto;
`;

export default function AppComponent(liferayParams) {
	/* eslint-disable no-console */
	// console.log('liferayParams', liferayParams, 'env', process.env);

	return (
		<div className="App">
			<ErrorBoundary>
				<Header>Calendar</Header>

				<Suspense fallback={<Loader />}>
					<div className="calendar-container">
						<LazyCalendar />
					</div>
				</Suspense>
			</ErrorBoundary>
		</div>
	);
}
