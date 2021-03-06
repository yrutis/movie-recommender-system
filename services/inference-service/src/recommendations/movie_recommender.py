# src/recommendations/movie_recommender.py

import pandas as pd

from src.controller.rating_controller import RatingController


class MovieRecommender:
    def __init__(self):
        """
        initialize MovieRecommender class
        """

        # get all ratings from the database
        self.ratings = RatingController.get_all_ratings()

    def get_movie_recommendations(self, rated_movie_list):
        """
        get recommended movie IDs based on a User that has similar movie preferences
        :param rated_movie_list: list of single rated movies
        :return a list of movies
        """

        # Check if the rated_movie_list is a list
        if not isinstance(rated_movie_list, list):
            raise TypeError("rated_movie_list should be of type list")

        # Convert incoming movies-rating list to a pandas dataframe
        incoming_ratings_df = pd.DataFrame(rated_movie_list)

        # Left join ratings DF with incoming ratings
        joined_ratings = pd.merge(
            self.ratings, incoming_ratings_df, on="movieId", how="inner"
        )

        # Calculate the absolute difference between the users' ratings and the client's rating
        joined_ratings["rating_dif"] = abs(
            joined_ratings["rating"] - joined_ratings["my_rating"]
        )

        # Group by userId and create a column to check the MAE and the count of movies
        new_df = joined_ratings.groupby("userId")["rating_dif"].agg(["sum", "count"])

        # Filter all the results where the sum is 0 and the count is 1. Why?
        # --> They are biased towards 1 movie rating that is the same
        new_df = new_df[~(new_df["sum"] == 0) | ~(new_df["count"] == 1)]

        # Weighted rating dif with Formula (total sum of rating dif / combined rated movies ** 2)
        # We use power of 2 to give more bias towards a movie rating match
        new_df["weighted_rating_dif"] = new_df["sum"] / (new_df["count"] ** 2)

        movie_list = []  # result movie list
        users_candidate_count = 0  # get the best match first and increase if this user has not rated sufficient movies
        remaining_movies = 50  # we want 50 movies in the list
        while remaining_movies != 0:
            # Get the UserId with the lowest weighted rating dif across the movies
            user_candidate = new_df.sort_values(
                by=["weighted_rating_dif", "count"], ascending=(True, False)
            ).iloc[users_candidate_count : users_candidate_count + 1]

            # Extract userId
            user_candidate = user_candidate.index.values[0]

            # Get movies from specific userId
            usersmovies = self.ratings.loc[self.ratings["userId"] == user_candidate]

            # sort according to userId's preference
            usersmovies = usersmovies.sort_values(by=["rating"], ascending=False).iloc[
                0:remaining_movies
            ]

            # Do not include movies the client has rated in the final movie list as a suggestion
            usersmovies = usersmovies[
                ~(usersmovies["movieId"].isin(incoming_ratings_df["movieId"]))
            ]

            # Extract movies with a rating of >=4
            usersmovies = usersmovies[usersmovies["rating"] >= 4]

            # Extract movieIds column to a list
            movie_list += usersmovies["movieId"].tolist()

            # Calculate how many remaining movies are left
            remaining_movies = 50 - len(movie_list)

            # Update the user candidate
            users_candidate_count += 1

        return movie_list
