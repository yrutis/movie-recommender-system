import pytest

from src.recommendations.movie_recommender import MovieRecommender


def test_init_movie_recommender():
    with pytest.raises(TypeError):
        MovieRecommender({})
