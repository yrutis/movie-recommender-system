from src.recommendations.movie_recommender import MovieRecommender
import pytest

def test_init_movie_recommender():
    with pytest.raises(TypeError) as e:
        MovieRecommender({})