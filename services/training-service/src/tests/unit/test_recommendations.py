from src.recommendations.movie_recommender import MovieRecommender


def test_movie_recommender(test_app):
    """
    test the movie recommender class
    """
    movie_recommender = MovieRecommender()
    movie_recommender.train_movie_recommendations()
