# src/api/train_recommendations.py


from flask import Blueprint, jsonify, make_response
from flask_restx import Api, Resource

from src.TrainingProcessor import TrainingProcessor

recommendations_blueprint = Blueprint("train-recommendations", __name__)
api = Api(recommendations_blueprint)


class TrainRecommendations(Resource):
    def get(self):
        process = TrainingProcessor()
        process.start()
        return make_response(jsonify(True), 202)


api.add_resource(TrainRecommendations, "/train-recommendations")
