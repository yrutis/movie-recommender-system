# src/api/ping.py

from flask import Blueprint, jsonify, make_response
from flask_restx import Api, Resource

ping_blueprint = Blueprint("ping", __name__)
api = Api(ping_blueprint)


class Ping(Resource):
    def get(self):
        return make_response(jsonify("pong"), 200)


api.add_resource(Ping, "/ping")
