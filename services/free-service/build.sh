#!/bin/bash
MVN_VERSION=$(mvn -q -Dexec.executable=echo -Dexec.args='${project.version}' --non-recursive exec:exec)
mvn install -DskipTests=true -Dmaven.javadoc.skip=true -B -V
mvn sonar:sonar -Dsonar.projectKey=mrs-free-service -Dsonar.host.url=https://sonaruzh.dev.eng.c-alm.ch -Dsonar.login=$SONAR_TOKEN
docker build -t noahcha/msr-free-service .
echo "$DOCKER_PASSWORD" | docker login -u "$DOCKER_USERNAME" --password-stdin
docker push noahcha/msr-free-service:${MVN_VERSION} && docker push noahcha/msr-free-service:latest
