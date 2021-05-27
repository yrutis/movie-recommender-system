from src.models.users import User


def test_create_users():
    """
    test if a user can be initalized
    """
    User(username="", password="", last_trained_on=None, tbl_rating_user_id=0)
