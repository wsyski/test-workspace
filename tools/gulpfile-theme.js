"use strict";

const gulp = require("gulp");

const liferayThemeTasks = require("liferay-theme-tasks");

const buildUtils = require('./BuildUtils');
const process = require("node:process");
const BuildUtils = new buildUtils({modulePath: process.cwd()});

BuildUtils.liferayTheme();
liferayThemeTasks.registerTasks({
  gulp
});
