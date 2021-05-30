# pull official base image
FROM python:3.7-slim-buster

# set working directory
WORKDIR /usr/src/app

# set environment variables
ENV PYTHONDONTWRITEBYTECODE 1
ENV PYTHONUNBUFFERED 1

# add and install requirements
COPY ./requirements.txt .

# install pytorch first
RUN pip install torch==1.7.1+cpu torchvision==0.8.2+cpu torchaudio==0.7.2 -f https://download.pytorch.org/whl/torch_stable.html

# --no-dependencies flag due to a fastAI dependencies missmatch on a cpu distribution
RUN pip install fastai==2.3.0 --no-dependencies

# install remaining dependencies
RUN pip install -r requirements.txt

# add app
COPY . .
EXPOSE 6000

# run server
CMD python manage.py run -h 0.0.0.0 -p 6000
