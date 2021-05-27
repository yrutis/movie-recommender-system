from sqlalchemy import Column, Float, Integer
from sqlalchemy.ext.declarative import declarative_base


class Rating(declarative_base()):
    """
    This is the model class of the Rating table in the database
    """

    __tablename__ = "tbl_rating"
    id = Column(Integer, primary_key=True, autoincrement=True)
    rating = Column(Float, nullable=False)
    tmdb_id = Column(Integer, nullable=False)
    user_id = Column(Integer, nullable=False)

    def __init__(self, rating, tmdb_id, user_id):
        self.rating = rating
        self.tmdb_id = tmdb_id
        self.user_id = user_id
