from src.models.ratings import Rating


def test_create_rating():
    """
    test if a rating can be initialized
    """
    Rating(rating=1, user_id=1, tmdb_id=1)
