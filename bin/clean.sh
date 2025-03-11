#!/bin/bash

#set -v -x
#set -u -e

isBuildOnly=false

OPTIND=1
while getopts :b c
do
  case ${c} in
  b)
    isBuildOnly=true ;;
  :)
    echo "Invalid empty option ${OPTARG} argument"
    exit 1 ;;
  \?) # Invalid option
    echo "Invalid option: ${OPTARG}"
    exit 2 ;;
  esac
done
shift $((OPTIND-1))
if [ $# -ne 0 ]
then
  echo "Usage: clean.sh [-b]"
  exit 4
fi

scriptDir="$(cd "$(dirname "$0")" && pwd)"
baseDir="${scriptDir}"/..

echo "baseDir: ${scriptDir} isBuildOnly: ${isBuildOnly}"
find ${baseDir} -name "node_modules" -prune -o -type d -name build -exec rm -rf {} \;
if [ ${isBuildOnly} = true ]
then
  exit 0
fi
find ${baseDir} -name package-lock.json -exec rm {} \;
#find ${baseDir} -name yarn.lock -exec rm {} \;
rm -rf ${baseDir}/node_modules_cache
rm -rf ${baseDir}/modules/frontend-js/.nx
find ${baseDir} -name .yarnrc -exec rm -rf {} \;
find ${baseDir} -name '*.css.map' -exec rm {} \;
find ${baseDir}/modules/frontend-js/packages/libs -name rollup.config.mjs -exec rm -rf {} \;
find ${baseDir} -name dist -exec rm -rf {} \;
find ${baseDir} -name out -exec rm -rf {} \;
find ${baseDir} -name out-tsc -exec rm -rf {} \;
find ${baseDir} -name node_modules -exec rm -rf {} \;
find ${baseDir} -name package-lock.json -exec rm {} \;
find ${baseDir} -name liferay-plugin.json -exec rm {} \;
find ${baseDir} -name liferay-theme.json -exec rm {} \;
find ${baseDir} -name liferay-npm-bundler-report.html -exec rm {} \;
find ${baseDir} -name liferay-theme.json -exec rm {} \;
find ${baseDir} -name node_modules -prune -o -path ${baseDir}/modules/frontend-js/packages/libs/packages/lib-liferay-mock -prune -o -type d -name public -exec rm -rf {} \;
