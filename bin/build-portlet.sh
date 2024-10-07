#!/usr/bin/env bash
#set -v -x
set -u -e

scriptDir=$(cd `dirname $0` && pwd)
portlet=${1:-react-ts-portlet}
echo "Building portlet: ${portlet} scriptDir: ${scriptDir}"
cd ${scriptDir}/../modules/frontend-js
yarn run clean
yarn run reset:nx
yarn run build:libs
cd -
cd ${scriptDir}/../modules/frontend-js/packages/react-provider
yarn run clean
yarn run build
yarn run deploy
cd -
cd ${scriptDir}/../modules/frontend-js/packages/${portlet}
yarn run clean
yarn run build
yarn run deploy
cd -
