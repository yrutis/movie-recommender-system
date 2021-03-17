#!/bin/bash
MVN_VERSION=$(mvn -q -Dexec.executable=echo -Dexec.args='${project.version}' --non-recursive exec:exec)
IMAGE_NAME=noahcha/msr-free-service
mvn install -DskipTests=true -Dmaven.javadoc.skip=true -B -V
mvn sonar:sonar -Dsonar.projectKey=mrs-free-service -Dsonar.host.url=https://sonaruzh.dev.eng.c-alm.ch -Dsonar.login=$SONAR_TOKEN
docker build -t $IMAGE_NAME .
docker tag $IMAGE_NAME "${IMAGE_NAME}:latest"
docker tag $IMAGE_NAME "${IMAGE_NAME}:${MVN_VERSION}"
echo "$DOCKER_PASSWORD" | docker login -u "$DOCKER_USERNAME" --password-stdin
docker push "${IMAGE_NAME}:${MVN_VERSION}" && docker push "${IMAGE_NAME}:latest"
