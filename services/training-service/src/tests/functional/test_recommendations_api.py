# src/tests/functional/test_recommendations_api.py

import json


def test_train_recommendations(test_app):
    client = test_app.test_client()
    resp = client.get("/train-recommendations")
    data = json.loads(resp.data.decode())
    assert resp.status_code == 202
