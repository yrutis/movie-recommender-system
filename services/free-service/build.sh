#!/bin/bash
echo $DOCKER_USERNAME
mvn org.apache.maven.plugins:maven-help-plugin:3.1.0:evaluate -Dexpression=project.version -q -DforceStdout
echo $version2
OUTPUT=`mvn org.apache.maven.plugins:maven-help-plugin:3.1.0:evaluate -Dexpression=project.version -q -DforceStdout`
echo "${OUTPUT}"
OUTPUT2=TEST22
echo "${OUTPUT2}"
echo ${OUTPUT2}
testv2=`echo ${DOCKER_USERNAME}`
echo "${testv2}"
