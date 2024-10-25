'use strict';

const gulp = require('gulp');
const process = require('node:process');
const argv = require('yargs').argv;
var isVersion = (argv.version === undefined) ? true : argv.version;

const buildUtils = require('./BuildUtils');
const BuildUtils = new buildUtils({modulePath: process.cwd()});

gulp.task('publish', (callback) => {
	BuildUtils.publish(isVersion);
	callback();
});
gulp.task('deploy', (callback) => {
	BuildUtils.deploy();
	callback();
});
