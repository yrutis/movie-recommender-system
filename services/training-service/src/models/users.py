from sqlalchemy import Column, Integer, String, Time
from sqlalchemy.ext.declarative import declarative_base


class User(declarative_base()):
    """
    This is the model class of the Rating table in the database
    """

    __tablename__ = "tbl_user"
    id = Column(Integer, primary_key=True, autoincrement=True)
    username = Column(String, nullable=False)
    password = Column(String, nullable=False)
    last_trained_on = Column(Time)
    tbl_rating_user_id = Column(Integer)

    def __init__(self, username, password, last_trained_on, tbl_rating_user_id):
        self.username = username
        self.password = password
        self.last_trained_on = last_trained_on
        self.tbl_rating_user_id = tbl_rating_user_id
