import os

import pytest

from src import create_app


@pytest.fixture(scope="function")
def test_app():
    """
    fixture for the test classes, set the environmental variable to testing
    :return:
    """
    app = create_app()
    app.config.from_object("src.config.TestingConfig")
    os.environ["TESTING"] = "1"
    print(os.getenv("TESTING"))
    with app.app_context():
        yield app  # testing happens here
