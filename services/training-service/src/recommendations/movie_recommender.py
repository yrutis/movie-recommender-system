# src/recommendations/movie_recommender.py

import pandas as pd
from fastai.data.external import untar_data

from src.controller.rating_controller import RatingController
from src.controller.user_controller import UserController

import torch
from fastai.tabular.all import *
from fastai.collab import *

import numpy as np


class MovieRecommender:
    def __init__(self):
        """
        initialize MovieRecommender class
        """

        # get all ratings
        self.ratings = RatingController.get_all_ratings()

        # get all signed in users
        self.users = UserController.get_all_users()

    def train_movie_recommendations(self):
        """
        run collaborative filtering
        :return:
        """

        # imitating a user; TODO remove
        self.users.at[0, 'tbl_rating_user_id'] = self.ratings['userId'].max()
        self.users.at[1, 'tbl_rating_user_id'] = self.ratings['userId'].min()
        print(self.users)

        dls = CollabDataLoaders.from_df(self.ratings, user_name='userId', item_name='movieId', rating_name='rating',
                                        bs=64)

        dls.show_batch()
        x, y = dls.one_batch()

        learn = collab_learner(dls, n_factors=50, y_range=(0, 5.5))

        # TODO change back to 5
        learn.fit_one_cycle(1, 5e-3, wd=0.1)

        # TODO: change for all
        # for predictions
        new_df = pd.DataFrame()
        new_df["movieId"] = self.ratings["movieId"]  # all movies
        new_df["userId"] = self.ratings['userId'].max()  # TODO change
        dl = learn.dls.test_dl(new_df)
        new_df["preds"] = learn.get_preds(dl=dl)[0]
        top_50_df = new_df.nlargest(50, 'preds')

        # TODO ratings add to database
        # TODO check error
        # RatingController.insert_ratings(ratings=top_50_df)

        # TODO check that not the same movie is not returned that is rated

        return True
