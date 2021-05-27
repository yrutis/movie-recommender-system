# src/api/train_recommendations.py
import os

from flask import Blueprint, jsonify, make_response
from flask_restx import Api, Resource

from src.training_processor.TrainingProcessor import TrainingProcessor
from src.recommendations.movie_recommender import MovieRecommender

recommendations_blueprint = Blueprint("train-recommendations", __name__)
api = Api(recommendations_blueprint)


class TrainRecommendations(Resource):
    def get(self):
        print(os.getenv('TESTING'))
        if int(os.getenv('TESTING')):
            movie_recommender = MovieRecommender()
            movies = movie_recommender.train_movie_recommendations()
        else:
            process = TrainingProcessor()
            process.start()
        return make_response(jsonify(True), 202)


api.add_resource(TrainRecommendations, "/train-recommendations")
