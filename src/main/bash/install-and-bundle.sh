#!/usr/bin/env bash
dirs=${project.build.directory}/swagger-appsscript/*
for dir in $dirs
 do
  if [[ -d $dir ]]; then
    if [[ "$dir" == *api ]]; then
      filename=$(basename "$dir")
      apiName=""
      if [[ $filename =~ (.*)(.*-api) ]]; then
        apiName=${BASH_REMATCH[1]}
      else
        echo "Not proper format";
      fi
      cd $dir
      output=${project.build.directory}/bundles
      if [ ! -d $output ]; then
        mkdir $output
      fi
      npm install
      outputFile=$output/$filename-bundle.js
      browserify -s $apiName app.js > $outputFile
      echo "Module $filename has been bundled and saved in : $outputFile"
    fi
  fi
done