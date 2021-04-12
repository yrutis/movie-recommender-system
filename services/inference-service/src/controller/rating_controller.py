import os

from sqlalchemy import create_engine
from sqlalchemy.orm import sessionmaker

from src.models.ratings import Rating
import pandas as pd

class RatingController:
    """
    Provides functionality to retrieve and send rating data from/to the database
    """

    @staticmethod
    def get_all_ratings():
        """
        get all ratings in the database
        :return:
        """

        # TODO: split in different file
        conn_url = os.getenv("DATABASE_URL")

        engine = create_engine(conn_url, echo=True)
        Session = sessionmaker(bind=engine)
        session = Session()

        # get all ratings
        ratings =  session.query(Rating).all()
        return  pd.DataFrame(
            [
                [rating.id, rating.rating, rating.tmdb_id, rating.user_id]
                for rating in ratings
            ],
            columns=["id", "rating", "movieId", "userId"],
        )
