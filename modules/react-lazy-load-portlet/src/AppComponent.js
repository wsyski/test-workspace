import React, { Suspense } from "react";
import styled from "styled-components";
import Loader from "./Loader";
import ErrorBoundary from "./ErrorBoundary";
import LazyCalendar from "./LazyCalendar";

const Header = styled.h1`
  position: absolute;
  width: 100%;
  margin: 100px auto;
`;

export default function AppComponent(liferayParams) {
	console.log('liferayParams', liferayParams, 'env', process.env);
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
