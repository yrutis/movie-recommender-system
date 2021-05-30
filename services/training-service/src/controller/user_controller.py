import os
from datetime import datetime

import pandas as pd
from sqlalchemy import create_engine
from sqlalchemy.orm import sessionmaker

from src.models.users import User


class UserController:
    """
    Provides functionality to retrieve and send user data from/to the database
    """

    @staticmethod
    def get_all_users():
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
        users = session.query(User).all()
        return pd.DataFrame(
            [
                [
                    user.id,
                    user.username,
                    user.password,
                    user.last_trained_on,
                    user.tbl_rating_user_id,
                ]
                for user in users
            ],
            columns=[
                "id",
                "username",
                "password",
                "last_trained_on",
                "tbl_rating_user_id",
            ],
        )

    @staticmethod
    def update_user_timestamp(users_without_ratings):
        """
        updates the timestamp of the last user training
        :return:
        """
        today = datetime.today()

        # create a db engine
        conn_url = os.getenv("DATABASE_URL")
        engine = create_engine(conn_url, echo=True)
        session_maker = sessionmaker(bind=engine)
        session = session_maker()

        # get all ratings
        users = session.query(User).all()

        for user in users:

            # If a user has not rated before, we will not update the timestamp for this specific user
            # we cannot just check here in case the user has rated in the before training started and now
            if user.username in users_without_ratings:
                continue

            # update the last trained on flag to reflect the current time
            user.last_trained_on = today

            # update the user
            session.add(user)
            session.commit()
