# src/recommendations/movie_recommender.py

import pandas as pd


class MovieRecommender:
    def __init__(self, rated_movie_list:[]):
        """
        initialize MovieRecommender class
        :param rated_movie_list: list of single rated movies
        """
        # Check if the rated_movie_list is a list
        if not isinstance(rated_movie_list, list):
            raise TypeError("rated_movie_list should be of type list")

        self.rated_movie_list = rated_movie_list

    def get_movie_recommendations(self):
        """
        get recommended movie IDs based on a User that has similar movie preferences
        :return:
        """

        # Convert incoming movies-rating list to a pandas dataframe
        incoming_ratings_df = pd.DataFrame(self.rated_movie_list)

        # TODO change to load from database
        # Load ratings
        ratings = pd.read_csv("ratings_small.csv")

        # Left join ratings DF with incoming ratings
        joined_ratings = pd.merge(
            ratings, incoming_ratings_df, on="movieId", how="inner"
        )

        # Calculate the absolute difference between the users' ratings and the client's rating
        joined_ratings["rating_dif"] = abs(
            joined_ratings["rating"] - joined_ratings["my_rating"]
        )

        # Group by userId and create a column to check the MAE and the count of movies
        new_df = joined_ratings.groupby("userId")["rating_dif"].agg(["sum", "count"])

        # Weighted rating dif with Formula (total sum of rating dif / combined rated movies)
        new_df["weighted_rating_dif"] = new_df["sum"] / new_df["count"]

        # TODO eliminate all users with less than xx combined movie ratings

        # Get the UserId with the lowest weighted rating dif across the movies
        user_candidate = new_df.sort_values(by=["weighted_rating_dif"]).iloc[0:1]

        # Extract userId
        user_candidate = user_candidate.index.values[0]

        # Get movies from specific userId
        usersmovies = ratings.loc[ratings["userId"] == user_candidate]

        # sort according to userId's preference
        usersmovies = usersmovies.sort_values(by=["rating"], ascending=False).iloc[0:50]

        # Extract movieIds column to a list
        movie_list = usersmovies["movieId"].tolist()

        return movie_list
