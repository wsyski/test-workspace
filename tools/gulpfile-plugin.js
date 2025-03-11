"use strict";

const gulp = require("gulp");

var liferayPluginTasks = require('liferay-theme-tasks/plugin');

const buildUtils = require('./BuildUtils');
const process = require("node:process");
const BuildUtils = new buildUtils({modulePath: process.cwd()});

BuildUtils.liferayPlugin();
liferayPluginTasks.registerTasks({
  gulp: gulp
});
