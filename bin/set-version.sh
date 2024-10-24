#!/bin/bash

#set -v -x
set -u -e

function fileRegexpReplace {
   local file=${1}
   local sedPattern=${2}
   sed -r "${sedPattern}" < "${file}" > "${file}.new"
   mv "${file}.new" "${file}"
}

OPTIND=1
while getopts : c
do
  case ${c} in
  :)
    echo "Invalid empty option ${OPTARG} argument"
    exit 1 ;;
  \?) # Invalid option
    echo "Invalid option: ${OPTARG}"
    exit 2 ;;
  esac
done
shift $((OPTIND-1))
if [ $# -ne 1 ]
then
  echo "Usage: set-version.sh <version>"
  exit 4
fi
version="${1}"

baseVersion=${version%%-SNAPSHOT}
scriptDir="$(cd "$(dirname "$0")" && pwd)"
baseDir="${scriptDir}"/..
echo "version: ${version} baseVersion: ${baseVersion} baseDir: ${scriptDir}"
fileRegexpReplace "${baseDir}"/gradle.properties "s/mavenVersion=.*$/mavenVersion=${version}/"
fileRegexpReplace "${baseDir}/modules/arena/arena-helper-provider/src/main/resources/META-INF/resources/index.js" "s/PROJECT_VERSION = (\"|').+\..+\..+(\"|')/PROJECT_VERSION = \"${baseVersion}\"/"
fileRegexpReplace "${baseDir}/modules/frontend-js/packages/libs/packages/lib-portlet-common/src/constants/PortletCommonConstants.ts" "s/PROJECT_VERSION = (\"|').+\..+\..+(\"|')/PROJECT_VERSION = '${baseVersion}'/"
fileRegexpReplace "${baseDir}/modules/calendar/calendar-event-list-web/src/main/resources/META-INF/resources/lib/index.ts" "s/calendar-event-list@.+\..+\..+\/lib\/bootstrap/calendar-event-list@${baseVersion}\/lib\/bootstrap/"
bndFiles=$(mktemp)
find "${baseDir}/modules" -type f -name bnd.bnd > "${bndFiles}"
while read -r bndFile
do
  fileRegexpReplace "${bndFile}" "s/Bundle-Version:.+\..+\..+/Bundle-Version: ${baseVersion}/"
  fileRegexpReplace "${bndFile}" "s/Fragment-Host:(.*);bundle-version=\".+\..+\..+\"/Fragment-Host:\1;bundle-version=\"${baseVersion}\"/"
done < "${bndFiles}"
rm "${bndFiles}"
packageFiles=$(mktemp)
find "${baseDir}/modules/frontend-js" -name "node_modules" -prune ,  -type f -name package.json > "${packageFiles}"
find "${baseDir}/modules/calendar" -name "node_modules" -prune ,  -type f -name package.json >> "${packageFiles}"
find "${baseDir}/themes" -name "node_modules" -prune ,  -type f -name package.json >> "${packageFiles}"
while read -r packageFile
do
  fileRegexpReplace "${packageFile}" "s/\"version\": \".+\..+\..+\"/\"version\": \"${baseVersion}\"/"
done < "${packageFiles}"
rm "${packageFiles}"
pluginFiles=$(mktemp)
find "${baseDir}/themes" -name "node_modules" -prune ,  -type f -name liferay-plugin-package.properties > "${pluginFiles}"
while read -r pluginFile
do
  fileRegexpReplace "${pluginFile}" "s/module-version=.+\..+\..+/module-version=${baseVersion}/"
done < "${pluginFiles}"
rm "${pluginFiles}"

echo "Version set to ${version}"
