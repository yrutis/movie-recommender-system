def test_train_recommendations(test_app):
    client = test_app.test_client()
    resp = client.get("/train-recommendations")
    assert resp.status_code == 202
