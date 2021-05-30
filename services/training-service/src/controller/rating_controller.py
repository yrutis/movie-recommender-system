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

        # create a db engine
        conn_url = os.getenv("DATABASE_URL")
        engine = create_engine(conn_url, echo=True)
        session_maker = sessionmaker(bind=engine)
        session = session_maker()

        # get all ratings
        ratings = session.query(Rating).all()
        return pd.DataFrame(
            [
                [rating.id, rating.rating, rating.tmdb_id, rating.user_id]
                for rating in ratings
            ],
            columns=["id", "rating", "movieId", "userId"],
        )

    @staticmethod
    def insert_ratings(ratings):
        """
        insert ratings in the database
        :return:
        """

        # create a db engine
        conn_url = os.getenv("DATABASE_URL")
        engine = create_engine(conn_url, echo=True)
        session_maker = sessionmaker(bind=engine)
        session = session_maker()

        # insert ratings
        for index, row in ratings.iterrows():
            rating = Rating(
                rating=row["rating"], user_id=row["userId"], tmdb_id=row["movieId"]
            )
            session.add(rating)
            session.commit()
            print("ratings inserted")

    @staticmethod
    def fix_autoincrement():
        # create a db engine
        conn_url = os.getenv("DATABASE_URL")
        engine = create_engine(conn_url, echo=True)

        # fix pkey autoincremment issue
        with engine.connect() as connection:
            result = connection.execute(
                "SELECT setval(pg_get_serial_sequence('tbl_rating', 'id'), coalesce(max(id)+1, 1), false) FROM tbl_rating;"
            )
            print("autoincrement error solved, resetting pkey to {}".format(result))
