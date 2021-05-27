from src.models.ratings import Rating
import numpy


def test_create_rating():
    """
    test if a rating can be initalized
    """
    print(numpy.__version__)
    Rating(rating=1, user_id=1, tmdb_id=1)
