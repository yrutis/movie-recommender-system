# src/recommendations/movie_recommender.py
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

        # set if data should be loaded from the database
        self._use_db = not bool(int(os.getenv('TESTING')))

        # get all ratings
        self.ratings = (
            RatingController.get_all_ratings()
            if self._use_db == 1
            else pd.read_csv(
                "ratings_small.csv", names=["id", "rating", "userId", "movieId"]
            )
        )

        # get all signed in users
        self.users = (
            UserController.get_all_users()
            if self._use_db == 1
            else pd.read_csv("users.csv", index_col=0)
        )

    def train_movie_recommendations(self):
        """
        run collaborative filtering
        :return:
        """

        # TODO env variable subset training

        # imitating a user; TODO remove
        self.users.at[0, "tbl_rating_user_id"] = self.ratings["userId"].max()
        self.users.at[1, "tbl_rating_user_id"] = self.ratings["userId"].min()

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

        learn.fit_one_cycle(5, 5e-3, wd=0.1)

        # for each user in our user database, get the top 100 rated movies
        for index, row in self.users.iterrows():

            # user has not rated any movies yet and hence should not get any recommendations
            if not row["tbl_rating_user_id"] or math.isnan(row["tbl_rating_user_id"]):
                continue

            # create a new dataframe for the test object for the fastai model
            new_df = pd.DataFrame()

            # include all movies
            new_df["movieId"] = self.ratings["movieId"]

            # include the current user
            new_df["userId"] = row["tbl_rating_user_id"]

            # create the fastai test object
            dl = learn.dls.test_dl(new_df)

            # safe the predictions in a new column
            new_df["rating"] = learn.get_preds(dl=dl)[0]

            # reduce the dataframe to the 100 top rated movie predictions
            new_df = new_df.nlargest(100, "rating")

            # if the user appears in the ratings and the top 100 predictions table
            # this means that the user has rated the movie before and hence knows the movie
            merged_table = new_df.merge(self.ratings, on=["userId", "movieId"])

            # if this is the case we have to remove these movies from the final predictions and
            # add more than the top 100 movies in the final list (we add the merge difference)
            new_df = new_df.nlargest(100 + merged_table.shape[0], "rating")
            new_df = new_df.loc[
                ~new_df["movieId"].isin(merged_table["movieId"].tolist())
            ]

            # fix autoincrement issue to
            RatingController.fix_autoincrement() if self._use_db else True

            print("insert ratings")
            RatingController.insert_ratings(new_df) if self._use_db else True

        # update users in database
        # TODO only if user has rated before
        UserController.update_user_timestamp() if self._use_db else True

        return "finished training: True"
