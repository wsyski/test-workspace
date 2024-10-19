'use strict';

const gulp = require('gulp');
const process = require('node:process');

const buildUtils = require('../../../../../../tools/BuildUtils');
const BuildUtils = new buildUtils({modulePath: process.cwd()});

gulp.task('publish', (callback) => {
	BuildUtils.publish(true);
	callback();
});
gulp.task('deploy', (callback) => {
	BuildUtils.deploy();
	callback();
});
