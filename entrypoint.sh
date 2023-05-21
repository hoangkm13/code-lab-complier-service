#!/usr/bin/env bash

profiles="";

addProfile () {
  if [ "$profiles" = "" ];
  then
    profiles=$1;
  else
    profiles=$profiles","$1;
  fi
}

mv /executions /compiler/executions

echo "Starting the compiler with the following profiles: "$profiles

java -jar -Dspring.profiles.active=$profiles /compiler.jar

