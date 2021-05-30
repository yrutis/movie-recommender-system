# src/api/ping.py


from flask import Blueprint
from flask_restx import Api, Resource

ping_blueprint = Blueprint("ping", __name__)
api = Api(ping_blueprint)


class Ping(Resource):
    """
    ping class (sanity check of microservice)
    """

    def get(self):
        return {"status": "success", "message": "pong222!"}


api.add_resource(Ping, "/ping")
