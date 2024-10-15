'use strict';

const gulp = require('gulp');
const buildUtils = require('../../tools/build-utils');
const liferayThemeTasks = require('liferay-theme-tasks');

liferayThemeTasks.registerTasks({
	gulp,
});

gulp.task('publish', function (cb) {
	buildUtils.publish(false);
	cb();
});
