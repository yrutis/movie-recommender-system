import os

import pandas as pd
from sqlalchemy import create_engine
from sqlalchemy.orm import sessionmaker

from src.models.ratings import Rating


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

        # connect to the database and create an engine and session
        conn_url = os.getenv("DATABASE_URL")
        engine = create_engine(conn_url, echo=True)
        Session = sessionmaker(bind=engine)
        session = Session()

        # get all ratings
        ratings = session.query(Rating).all()

        # safe ratings in a pandas dataframe
        return pd.DataFrame(
            [
                [rating.id, rating.rating, rating.tmdb_id, rating.user_id]
                for rating in ratings
            ],
            columns=["id", "rating", "movieId", "userId"],
        )
