# src/api/recommendations.py


from flask import Blueprint, request
from flask_restx import Api, Resource, fields

from src.recommendations.movie_recommender import MovieRecommender

recommendations_blueprint = Blueprint("recommendations", __name__)
api = Api(recommendations_blueprint)

# Movie ratings is composed of single rated movies
movie_ratings_model = api.model(
    "MovieRatings",
    {
        "movieId": fields.Integer(required=True),
        "my_rating": fields.Integer(required=True),
    },
)


class Recommendations(Resource):

    # We expect a list of single rated movies
    @api.expect([movie_ratings_model], validate=True)
    def post(self):

        # get the movie ratings
        movie_ratings = request.get_json()

        # Create empty response object
        response_object = {}

        # Check if there are 12 rated movies
        if len(movie_ratings) != 12:
            response_object[
                "message"
            ] = "12 rated movies are needed to give a recommendation. Please try again!"
            return response_object, 400

        # Check if no ratings are out of bound (have to be between 0 and 5)
        for single_movie_rating in movie_ratings:
            if (
                single_movie_rating["my_rating"] < 1
                or single_movie_rating["my_rating"] > 5
            ):
                response_object[
                    "message"
                ] = "One of the ratings is not between 0 and 5. Please try again!"
                return response_object, 400

        # create an instance of the recommender class given the client's preferences
        movie_recommender = MovieRecommender(movie_ratings)

        # get the movie recommendations
        movie_recommendations = movie_recommender.get_movie_recommendations()

        # construct the response object
        response_object = {"message": movie_recommendations}

        return response_object, 201


api.add_resource(Recommendations, "/recommendations")
