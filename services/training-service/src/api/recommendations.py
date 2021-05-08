# src/api/recommendations.py


from flask import Blueprint, request
from flask_restx import Api, Resource, fields
from fastai.tabular.all import *
from fastai.collab import *
import pandas as pd
import numpy as np
import torch

from src.recommendations.movie_recommender import MovieRecommender
from src.controller.user_controller import UserController

recommendations_blueprint = Blueprint("recommendations", __name__)
api = Api(recommendations_blueprint)

# Create an instance of the MovieRecommender class

movie_recommender = MovieRecommender(use_db=0)


class Recommendations(Resource):

    def get(self):
        movies = movie_recommender.train_movie_recommendations()
        return movies


api.add_resource(Recommendations, "/recommendations")
