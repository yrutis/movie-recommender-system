#!/bin/bash
PYTHON_VERSION=$(python _version.py)
IMAGE_NAME=noahcha/mrs-inference-service
pytest -v -o junit_family=xunit1 --cov=src --cov-report xml:test-results/coverage.xml --junitxml=test-results/results.xml
sonar-scanner -X -Dsonar.projectKey=mrs-inference-service -Dsonar.sources=. -Dsonar.host.url=https://sonaruzh.dev.eng.c-alm.ch -Dsonar.login=$SONAR_TOKEN -Dsonar.language=py -Dsonar.source=src -Dsonar.sourceEncoding=UTF-8 -Dsonar.python.xunit.reportPath=test-results/results.xml -Dsonar.python.coverage.reportPaths=test-results/coverage.xml -Dsonar.python.coveragePlugin=cobertura -Dsonar.exclusions=src/tests/**,test-results/**,coverage-reports/htmlcov
docker build -t $IMAGE_NAME .
docker tag $IMAGE_NAME "${IMAGE_NAME}:latest"
docker tag $IMAGE_NAME "${IMAGE_NAME}:${PYTHON_VERSION}"
echo "$DOCKER_PASSWORD" | docker login -u "$DOCKER_USERNAME" --password-stdin
docker push "${IMAGE_NAME}:${PYTHON_VERSION}" && docker push "${IMAGE_NAME}:latest"