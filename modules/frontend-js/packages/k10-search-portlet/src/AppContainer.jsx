import {LiferayParamsContext, LiferayUtil} from '@arena/lib-portlet-common';
import {ClayIconSpriteContext} from '@clayui/icon';
import React from 'react';
import {HashRouter as Router, Route, Switch} from 'react-router-dom';

import App from './App';
import RecordWrapper from './components/RecordWrapper';
import SearchResultWrapper from './components/SearchResultWrapper';

const AppContainer = ({liferayParams}) => {
	return (
		<div data-test-id="appContainer">
			<ClayIconSpriteContext.Provider
				value={LiferayUtil.getClaySpritemap()}
			>
				<LiferayParamsContext.Provider value={liferayParams}>
					<App>
						<Router>
							<Switch>
								<Route
									path="/record/:id"
									render={(props) => {
										return (
											<RecordWrapper
												id={props.match.params.id}
												{...props}
											/>
										);
									}}
								/>

								<Route
									component={SearchResultWrapper}
									exact
									path=""
								/>
							</Switch>
						</Router>
					</App>
				</LiferayParamsContext.Provider>
			</ClayIconSpriteContext.Provider>
		</div>
	);
};

export default AppContainer;
