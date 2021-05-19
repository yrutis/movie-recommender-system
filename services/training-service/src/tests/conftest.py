# src/tests/conftest.py


import pandas as pd
import pytest

from src import create_app


@pytest.fixture(scope="function")
def test_app(mocker):
    df = pd.read_csv("ratings_small.csv", names=["id", "rating", "movieId", "userId"])
    mocker.patch(
        "src.controller.rating_controller.RatingController.get_all_ratings",
        return_value=df,
    )
    app = create_app()
    app.config.from_object("src.config.TestingConfig")
    with app.app_context():
        yield app  # testing happens here
