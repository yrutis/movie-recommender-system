# src/api/ping.py


from flask import Blueprint
from flask_restx import Api, Resource
from src.controller.user_controller import UserController
from src.controller.rating_controller import RatingController

ping_blueprint = Blueprint("ping", __name__)
api = Api(ping_blueprint)


class Ping(Resource):
    def get(self):
        # UserController.update_user_timestamp()
        RatingController.insert_ratings(ratings=[])
        return {"status": "success", "message": "pong!"}


api.add_resource(Ping, "/ping")
