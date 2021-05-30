from flask import Blueprint
from flask_restx import Api, Resource

ping_blueprint = Blueprint("ping", __name__)
api = Api(ping_blueprint)


class Ping(Resource):
    """
    sanitize check
    """

    def get(self):
        """
        sanatize check if microservice is running
        :return: pong
        """
        return {"status": "success", "message": "pong!"}


# add the ping endpoint to the flask resources
api.add_resource(Ping, "/ping")
