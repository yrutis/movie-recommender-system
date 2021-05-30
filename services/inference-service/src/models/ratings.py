from sqlalchemy import Column, Float, Integer
from sqlalchemy.ext.declarative import declarative_base


class Rating(declarative_base()):
    """
    Rating class which reflects the table rating in the database
    """

    __tablename__ = "tbl_rating"
    id = Column(Integer, primary_key=True, autoincrement=True)
    rating = Column(Float, nullable=False)
    tmdb_id = Column(Integer, nullable=False)
    user_id = Column(Integer, nullable=False)

    def __init__(self, rating, tmdb_id, user_id):
        """
        :param rating: a rating (int)
        :param tmdb_id: a tmdb id (int)
        :param user_id: a user id (int)
        """
        self.rating = rating
        self.tmdb_id = tmdb_id
        self.user_id = user_id
