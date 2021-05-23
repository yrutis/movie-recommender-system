# src/api/check_task.py
from celery.result import AsyncResult
from flask import Blueprint, jsonify, make_response
from flask_restx import Api, Resource

task_blueprint = Blueprint("tasks", __name__)
api = Api(task_blueprint)


class Task(Resource):
    def get(self, task_id):
        task_result = AsyncResult(task_id)
        result = {
            "task_id": task_id,
            "task_status": task_result.status,
            "task_result": task_result.result,
        }
        return make_response(jsonify(result), 200)


api.add_resource(Task, "/tasks/<task_id>")
