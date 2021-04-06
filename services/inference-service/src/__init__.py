# src/__init__.py


import os

from flask import Flask


def create_app(script_info=None):
    """
    Create flask app
    :param script_info: script info
    :return: app
    """

    # instantiate the app
    app = Flask(__name__)

    # set config
    app_settings = os.getenv("APP_SETTINGS")
    app.config.from_object(app_settings)

    # register blueprints
    from src.api.ping import ping_blueprint

    app.register_blueprint(ping_blueprint)

    # register blueprints
    from src.api.recommendations import recommendations_blueprint

    app.register_blueprint(recommendations_blueprint)

    # shell context for flask cli
    @app.shell_context_processor
    def ctx():
        return {"app": app}

    return app
