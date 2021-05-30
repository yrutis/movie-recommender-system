#!/bin/bash
PACKAGE_VERSION=$(grep -m1 version package.json | awk -F: '{ print $2 }' | sed 's/[", ]//g')
IMAGE_NAME=noahcha/mrs-webapp
npm ci
npm run build:prod
npm run cypress:ci
sonar-scanner -Dsonar.projectKey=mrs-webapp -Dsonar.sources=. -Dsonar.host.url=https://sonaruzh.dev.eng.c-alm.ch -Dsonar.login=$SONAR_TOKEN
docker build -t $IMAGE_NAME .
docker tag $IMAGE_NAME "${IMAGE_NAME}:latest"
docker tag $IMAGE_NAME "${IMAGE_NAME}:${PACKAGE_VERSION}"
echo "$DOCKER_PASSWORD" | docker login -u "$DOCKER_USERNAME" --password-stdin
docker push "${IMAGE_NAME}:${PACKAGE_VERSION}" && docker push "${IMAGE_NAME}:latest"
