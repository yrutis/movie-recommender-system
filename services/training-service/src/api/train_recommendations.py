import os

from flask import Blueprint, Response
from flask_restx import Api, Resource

from src.recommendations.movie_recommender import MovieRecommender
from src.training_processor.TrainingProcessor import TrainingProcessor

recommendations_blueprint = Blueprint("train-recommendations", __name__)
api = Api(recommendations_blueprint)


class TrainRecommendations(Resource):
    """
    Endpoint to start the training of the movie recommender system
    """

    def get(self):
        """
        Entrypoint of the GET request to start the training of the movie recommender system
        """

        # In case of testing, start the training in the same process and return once training finished
        if int(os.getenv("TESTING")):
            movie_recommender = MovieRecommender()
            movie_recommender.train_movie_recommendations()

        # Otherwise start the training in another process and return true straight away
        else:
            process = TrainingProcessor()
            process.start()

        # return status code 202 which implies that the process has started but not finished yet
        return Response(response="true", status=202)


api.add_resource(TrainRecommendations, "/train-recommendations")
