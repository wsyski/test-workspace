'use strict';

var gulp = require('gulp');

var buildUtils = require('../../../../tools/build-utils');

gulp.task('publish', (cb) => {
	buildUtils.publish();
	cb();
});
gulp.task('deploy', (cb) => {
	buildUtils.deploy();
	cb();
});
