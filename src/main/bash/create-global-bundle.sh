#!/usr/bin/env bash
dir=${project.build.directory}/swagger-appsscript/

outputFile=${project.build.directory}/global-bundle.js
browserify -s swagger $dir/app.js > $outputFile
echo "Module $filename has been bundled and saved in : $outputFile"