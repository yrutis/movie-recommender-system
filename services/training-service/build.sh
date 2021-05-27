#!/bin/bash

# Build
PYTHON_VERSION=$(python _version.py)
echo "PYTHON_VERSION"
IMAGE_NAME=noahcha/mrs-training-service
docker build -t $IMAGE_NAME .
docker tag $IMAGE_NAME "${IMAGE_NAME}:latest"
docker tag $IMAGE_NAME "${IMAGE_NAME}:${PYTHON_VERSION}"
echo "$DOCKER_PASSWORD" | docker login -u "$DOCKER_USERNAME" --password-stdin

# create remote repo
docker push "${IMAGE_NAME}:${PYTHON_VERSION}" && docker push "${IMAGE_NAME}:latest"

# Install dependencies
python3.7 -m venv env
source env/bin/activate
pip install torch==1.7.1+cpu torchvision==0.8.2+cpu torchaudio==0.7.2 -f https://download.pytorch.org/whl/torch_stable.html
pip install fastai==2.3.0 --no-dependencies
pip install -r requirements.txt

# Run linting tools
flake8 src
black src --check
isort src --check-only

# Run tests and send test report to sonar scanner
pytest "src/tests" -p no:warnings
pytest -v -o junit_family=xunit1 --cov=src --cov-report xml:test-results/coverage.xml --junitxml=test-results/results.xml
sonar-scanner -Dsonar.projectKey=mrs-inference-service -Dsonar.sources=. -Dsonar.host.url=https://sonaruzh.dev.eng.c-alm.ch -Dsonar.login=$SONAR_TOKEN -Dsonar.language=py -Dsonar.source=src -Dsonar.sourceEncoding=UTF-8 -Dsonar.python.xunit.reportPath=test-results/results.xml -Dsonar.python.coverage.reportPaths=test-results/coverage.xml -Dsonar.python.coveragePlugin=cobertura -Dsonar.exclusions=src/tests/**,test-results/**,coverage-reports/htmlcov
