import pytest

from src import create_app
import pandas as pd


@pytest.fixture(scope="function")
def test_app(mocker):
    """
    function that gets called before each test. Instead of connecting to the database, a subset of ratings is loaded from a csv file
    :param mocker: replace the get all ratings return value by the dataframe
    :return: flask app ready for testing
    """
    # load the ratings file
    df = pd.read_csv('ratings_small.csv', names=["id", "rating", "movieId", "userId"])

    # replace the get all ratings method with the dataframe
    mocker.patch('src.controller.rating_controller.RatingController.get_all_ratings', return_value=df)

    # create the flask app and apply the testing config
    app = create_app()
    app.config.from_object("src.config.TestingConfig")
    with app.app_context():
        yield app  # testing happens here
