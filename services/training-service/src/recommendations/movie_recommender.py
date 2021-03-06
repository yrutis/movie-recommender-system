import math
import os

import pandas as pd
from fastai.collab import CollabDataLoaders, collab_learner

from src.controller.rating_controller import RatingController
from src.controller.user_controller import UserController


class MovieRecommender:
    def __init__(self):
        """
        initialize MovieRecommender class
        """

        # set if data should be loaded from the database, in case of testing, this variable is set to false
        self._use_db = not bool(int(os.getenv("TESTING")))

        # get all ratings
        self.ratings = (
            RatingController.get_all_ratings()
            if self._use_db
            else pd.read_csv(
                "ratings_small.csv", names=["id", "rating", "userId", "movieId"]
            )
        )

        # get all signed in users
        self.users = (
            UserController.get_all_users()
            if self._use_db
            else pd.read_csv("users.csv", index_col=0)
        )

    def train_movie_recommendations(self):
        """
        run collaborative filtering
        :return:
        """

        # create the Pytorch CollabDataLoader object for the collaborative filtering learner
        dls = CollabDataLoaders.from_df(
            self.ratings,
            user_name="userId",
            item_name="movieId",
            rating_name="rating",
            bs=64,
        )

        # create the Collaborative filtering learner
        learn = collab_learner(dls, n_factors=50, y_range=(0, 5.5))

        # train on 5 epochs, if in testing mode train only 1 epoch
        learn.fit_one_cycle(5, 5e-3, wd=0.1) if self._use_db else learn.fit_one_cycle(
            1, 5e-3, wd=0.1
        )

        # safe all users without ratings in a list
        users_without_ratings = []

        # for each user in our user database, get the top 100 rated movies
        for index, row in self.users.iterrows():

            # user has not rated any movies yet and hence should not get any recommendations
            if not row["tbl_rating_user_id"] or math.isnan(row["tbl_rating_user_id"]):
                users_without_ratings.append(row["username"])
                continue

            # create a new dataframe for the test object for the fastai model
            new_df = pd.DataFrame()

            # include all movies
            new_df["movieId"] = self.ratings["movieId"].unique()

            # include the current user
            new_df["userId"] = row["tbl_rating_user_id"]

            # create the fastai test object
            dl = learn.dls.test_dl(new_df)

            # safe the predictions in a new column
            new_df["rating"] = learn.get_preds(dl=dl)[0]

            # reduce the dataframe to the 100 top rated movie predictions
            new_df = new_df.nlargest(100, "rating")

            # insert ratings in db
            RatingController.insert_ratings(new_df) if self._use_db else True

        # update users in database
        UserController.update_user_timestamp(
            users_without_ratings
        ) if self._use_db else True
