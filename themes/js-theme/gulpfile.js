'use strict';

const gulp = require('gulp');

const process = require('node:process');

const buildUtils = require('../../tools/BuildUtils');
const BuildUtils = new buildUtils({modulePath: process.cwd()});

const liferayThemeTasks = require('liferay-theme-tasks');

liferayThemeTasks.registerTasks({
	gulp,
});

gulp.task('publish', function (callback) {
	BuildUtils.publish(false);
	callback();
});
