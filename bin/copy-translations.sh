#!/usr/bin/env bash
#set -x -v

function copyFile {
    local fileAbsoluteName=$1
    local filePath=${fileAbsoluteName%/*}
    local fileNameExtension=${fileAbsoluteName##*/}
    local fileName=${fileNameExtension%%.*}
    local fileExtension=${fileNameExtension##*.}
    if [ ${fileName} = 'translation' ]
    then
      if [ ! -f "${filePath}/../translation.json" ]
      then
        cp "${filePath}/en_GB/${fileNameExtension}" "${fileAbsoluteName}"
      fi
    elif [ ${fileName} = 'Language' ]
    then
      cp "${filePath}/${fileName}_en_GB.${fileExtension}" "${fileAbsoluteName}"
    fi

}
export -f copyFile

scriptDir=$(cd `dirname $0` && pwd)
echo "scriptDir: ${scriptDir}"
find ${scriptDir}/.. -type f -not -path '*/node_modules/*' '(' -name "translation.json" -o -name "Language.properties" ')' -exec bash -c 'copyFile "$1"' - {} \;
