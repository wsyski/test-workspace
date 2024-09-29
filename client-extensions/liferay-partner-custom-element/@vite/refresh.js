/* eslint-disable @liferay/no-absolute-import */

/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

// @ts-ignore

import RefreshRuntime from '/@react-refresh';

/**
 * @description This file is used ONLY and EXCLUSIVE for development
 * When setting up the remote app, add this import
 * http://localhost:5173/@vite/refresh.js
 */

RefreshRuntime.injectIntoGlobalHook(window);
window.$RefreshReg$ = () => {};
window.$RefreshSig$ = () => (type) => type;

window.__vite_plugin_react_preamble_installed__ = true;
