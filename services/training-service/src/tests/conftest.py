# src/tests/conftest.py
import os

import pandas as pd
import pytest

from src import create_app
from src import config


@pytest.fixture(scope="function")
def test_app(mocker):
    df = pd.read_csv("ratings_small.csv", names=["id", "rating", "movieId", "userId"])
    mocker.patch(
        "src.controller.rating_controller.RatingController.get_all_ratings",
        return_value=df,
    )
    df = pd.read_csv("users.csv", index_col=0)
    mocker.patch(
        "src.controller.user_controller.UserController.get_all_users",
        return_value=df,
    )
    app = create_app()
    app.config.from_object("src.config.TestingConfig")
    os.environ['TESTING'] = '1'
    print(os.getenv('TESTING'))
    with app.app_context():
        yield app  # testing happens here
