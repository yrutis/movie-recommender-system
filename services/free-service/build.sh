#!/bin/bash
MVN_VERSION=$(mvn -q -Dexec.executable=echo -Dexec.args='${project.version}' --non-recursive exec:exec)
echo "${MVN_VERSION}"
