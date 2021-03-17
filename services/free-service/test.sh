#!/bin/bash
# OUTPUT=$(mvn org.apache.maven.plugins:maven-help-plugin:3.1.0:evaluate -Dexpression=project.version -q)
# echo "${OUTPUT}"
# OUTPUT2=`mvn org.apache.maven.plugins:maven-help-plugin:3.1.0:evaluate -Dexpression=project.version -q`
# echo "${OUTPUT2}"
#

MVN_VERSION=$(mvn -q -Dexec.executable=echo -Dexec.args='${project.version}' --non-recursive exec:exec)
echo "${MVN_VERSION}"
