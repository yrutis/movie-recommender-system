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

        # TODO: split in different file
        conn_url = os.getenv("DATABASE_URL")

        engine = create_engine(conn_url, echo=True)
        Session = sessionmaker(bind=engine)
        session = Session()

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
    def update_user_timestamp():
        """
        updates the timestamp of the last user training
        :return:
        """
        today = datetime.today()

        # TODO: split in different file
        conn_url = os.getenv("DATABASE_URL")

        engine = create_engine(conn_url, echo=True)
        Session = sessionmaker(bind=engine)
        session = Session()

        # get all ratings
        users = session.query(User).all()
        for user in users:
            user.last_trained_on = today
            # print('this is the user {}'.format(user))
            session.add(user)
            session.commit()
            # print("user updated")
