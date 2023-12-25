import React from 'react';
import ReactDOM from 'react-dom';

import AppComponent from './AppComponent';

/**
 * This is the main entry point of the portlet.
 *
 * See https://tinyurl.com/js-ext-portlet-entry-point for the most recent 
 * information on the signature of this function.
 *
 * @param  {Object} params a hash with values of interest to the portlet
 * @return {void}
 */
export default function main(params) { 
	 ReactDOM.render(
		<AppComponent 
			configuration={params.configuration} 
			contextPath={params.contextPath}
			portletElementId={params.portletElementId}
			portletNamespace={params.portletNamespace}
		 />, 
		document.getElementById(params.portletElementId)
	);
}
