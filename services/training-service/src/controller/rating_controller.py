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
            with engine.connect() as connection:
                id = (
                    connection.execute("SELECT MAX(id) FROM tbl_rating;").first()[0] + 1
                )
            rating = Rating(
                id=id,
                rating=row["rating"],
                user_id=int(row["userId"]),
                tmdb_id=int(row["movieId"]),
            )
            session.add(rating)
            session.commit()
            print("ratings inserted")
