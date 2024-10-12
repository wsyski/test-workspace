#!/usr/bin/env bash
scriptDir="$(cd "$(dirname "$0")" && pwd)"
echo "scriptDir: ${scriptDir}"
find ${scriptDir}/.. -name yarn.lock -exec rm {} \;
rm -rf ${scriptDir}/../node_modules_cache
rm -rf ${scriptDir}/../modules/frontend-js/.nx
find ${scriptDir}/.. -name .yarnrc -exec rm -rf {} \;
find ${scriptDir}/.. -name build -exec rm -rf {} \;
find ${scriptDir}/.. -name dist -exec rm -rf {} \;
find ${scriptDir}/.. -name out -exec rm -rf {} \;
find ${scriptDir}/.. -name out-tsc -exec rm -rf {} \;
find ${scriptDir}/.. -name node_modules -exec rm -rf {} \;
find ${scriptDir}/.. -name package-lock.json -exec rm {} \;
find ${scriptDir}/.. -name liferay-plugin.json -exec rm {} \;
find ${scriptDir}/.. -name liferay-theme.json -exec rm {} \;
find ${scriptDir}/.. -name liferay-npm-bundler-report.html -exec rm {} \;
find ${scriptDir}/.. -name liferay-theme.json -exec rm {} \;
find ${scriptDir}/.. -type d -name 'public' -exec rm -rf {}/o \;
