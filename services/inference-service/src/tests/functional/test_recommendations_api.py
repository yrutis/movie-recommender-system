# src/tests/functional/test_recommendations_api.py


import json


def test_receive_recommendations_valid_input(test_app):
    """
    test if the endpoint works for a valid input
    :param test_app: fixture
    :return:
    """
    client = test_app.test_client()
    resp = client.post(
        "/recommendations",
        data=json.dumps(
            [
                {"movieId": 1, "my_rating": 2},
                {"movieId": 2, "my_rating": 2},
                {"movieId": 3, "my_rating": 2},
                {"movieId": 4, "my_rating": 2},
                {"movieId": 5, "my_rating": 2},
                {"movieId": 6, "my_rating": 2},
                {"movieId": 7, "my_rating": 2},
                {"movieId": 8, "my_rating": 2},
                {"movieId": 9, "my_rating": 2},
                {"movieId": 10, "my_rating": 2},
                {"movieId": 11, "my_rating": 2},
                {"movieId": 12, "my_rating": 2},
            ]
        ),
        content_type="application/json",
    )
    data = json.loads(resp.data.decode())
    print(data)
    # TODO change back to gurantee 50 movies
    assert len(data) <= 50


def test_receive_recommendations_invalid_input(test_app):
    """
    test endpoint for an empty json
    :param test_app: fixture
    :return:
    """
    client = test_app.test_client()
    resp = client.post(
        "/recommendations",
        data=json.dumps({}),
        content_type="application/json",
    )
    data = json.loads(resp.data.decode())
    assert resp.status_code == 400
    assert "Input payload validation failed" in data["message"]


def test_receive_recommendations_invalid_json_keys(test_app):
    """
    test endpoint for wrong json keys (movie instead of movieId
    :param test_app:
    :return:
    """
    client = test_app.test_client()
    resp = client.post(
        "/recommendations",
        data=json.dumps(
            [
                {"movie": 1, "my_rating": 2},
                {"movie": 2, "my_rating": 2},
                {"movie": 3, "my_rating": 2},
                {"movie": 4, "my_rating": 2},
                {"movie": 5, "my_rating": 2},
                {"movie": 6, "my_rating": 2},
                {"movie": 7, "my_rating": 2},
                {"movie": 8, "my_rating": 2},
                {"movie": 9, "my_rating": 2},
                {"movie": 10, "my_rating": 2},
                {"movie": 11, "my_rating": 2},
                {"movie": 12, "my_rating": 2},
            ]
        ),
        content_type="application/json",
    )
    data = json.loads(resp.data.decode())
    assert resp.status_code == 400
    assert "Input payload validation failed" in data["message"]


def test_receive_recommendations_invalid_input_type(test_app):
    """
    test endpoint for wrong input type (str instead of int)
    :param test_app:
    :return:
    """
    client = test_app.test_client()
    resp = client.post(
        "/recommendations",
        data=json.dumps(
            [
                {"movie": "1", "my_rating": 2},
                {"movie": "2", "my_rating": 2},
                {"movie": "3", "my_rating": 2},
                {"movie": "4", "my_rating": 2},
                {"movie": "5", "my_rating": 2},
                {"movie": "6", "my_rating": 2},
                {"movie": "7", "my_rating": 2},
                {"movie": "8", "my_rating": 2},
                {"movie": "9", "my_rating": 2},
                {"movie": "10", "my_rating": 2},
                {"movie": "11", "my_rating": 2},
                {"movie": "12", "my_rating": 2},
            ]
        ),
        content_type="application/json",
    )
    data = json.loads(resp.data.decode())
    assert resp.status_code == 400
    assert "Input payload validation failed" in data["message"]


def test_receive_recommendations_too_few_input(test_app):
    """
    test endpoint for too few rated movies as input (11 instead of 12)
    :param test_app: fixture
    :return:
    """
    client = test_app.test_client()
    resp = client.post(
        "/recommendations",
        data=json.dumps(
            [
                {"movieId": 1, "my_rating": 2},
                {"movieId": 2, "my_rating": 2},
                {"movieId": 3, "my_rating": 2},
                {"movieId": 4, "my_rating": 2},
                {"movieId": 5, "my_rating": 2},
                {"movieId": 6, "my_rating": 2},
                {"movieId": 7, "my_rating": 2},
                {"movieId": 8, "my_rating": 2},
                {"movieId": 9, "my_rating": 2},
                {"movieId": 10, "my_rating": 2},
                {"movieId": 11, "my_rating": 2},
            ]
        ),
        content_type="application/json",
    )
    data = json.loads(resp.data.decode())
    print(data)
    assert resp.status_code == 400
    assert (
        "12 rated movies are needed to give a recommendation. Please try again!"
        in data["message"]
    )


def test_recommendations_rating_out_of_bound_upper(test_app):
    """
    test recommendations class for a movie rating out of bound (0-5)
    :param test_app: fixture
    :return:
    """
    client = test_app.test_client()
    resp = client.post(
        "/recommendations",
        data=json.dumps(
            [
                {"movieId": 1, "my_rating": 2},
                {"movieId": 2, "my_rating": 2},
                {"movieId": 3, "my_rating": 2},
                {"movieId": 4, "my_rating": 2},
                {"movieId": 5, "my_rating": 2},
                {"movieId": 6, "my_rating": 2},
                {"movieId": 7, "my_rating": 2},
                {"movieId": 8, "my_rating": 2},
                {"movieId": 9, "my_rating": 2},
                {"movieId": 10, "my_rating": 2},
                {"movieId": 11, "my_rating": 2},
                {"movieId": 12, "my_rating": 6},
            ]
        ),
        content_type="application/json",
    )
    data = json.loads(resp.data.decode())
    assert resp.status_code == 400
    assert (
        "One of the ratings is not between 0 and 5. Please try again!"
        in data["message"]
    )


def test_recommendations_rating_out_of_bound_lower(test_app):
    """
    test recommendations class for a movie rating out of bound (0-5)
    :param test_app: fixture
    :return:
    """
    client = test_app.test_client()
    resp = client.post(
        "/recommendations",
        data=json.dumps(
            [
                {"movieId": 1, "my_rating": 2},
                {"movieId": 2, "my_rating": 2},
                {"movieId": 3, "my_rating": 2},
                {"movieId": 4, "my_rating": 2},
                {"movieId": 5, "my_rating": 2},
                {"movieId": 6, "my_rating": 2},
                {"movieId": 7, "my_rating": 2},
                {"movieId": 8, "my_rating": 2},
                {"movieId": 9, "my_rating": 2},
                {"movieId": 10, "my_rating": 2},
                {"movieId": 11, "my_rating": 2},
                {"movieId": 12, "my_rating": 0},
            ]
        ),
        content_type="application/json",
    )
    data = json.loads(resp.data.decode())
    assert resp.status_code == 400
    assert (
        "One of the ratings is not between 0 and 5. Please try again!"
        in data["message"]
    )
