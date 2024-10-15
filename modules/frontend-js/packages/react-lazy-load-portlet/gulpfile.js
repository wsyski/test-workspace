'use strict';

const gulp = require('gulp');

const buildUtils = require('../../../../tools/build-utils');

gulp.task('publish', (cb) => {
	buildUtils.publish();
	cb();
});
gulp.task('deploy', (cb) => {
	buildUtils.deploy();
	cb();
});

