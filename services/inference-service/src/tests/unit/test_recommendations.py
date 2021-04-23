# import pytest
#
# from src.recommendations.movie_recommender import MovieRecommender
#
#
# def test_init_movie_recommender(setup_movie_recommender):
#     """
#     test the init of a wrong rated movie list type (e.g. dict instead of list)
#     :return:
#     """
#     with pytest.raises(TypeError):
#         movie_recommender(rated_movie_list={})
#
#
# def test_receive_50_movie_recommendations():
#     """
#     normal case: check that the top 50 rated movies from a user are returned
#     :return:
#     """
#     movie_recommender = MovieRecommender(
#         [
#             {"movieId": 1, "my_rating": 2},
#             {"movieId": 2, "my_rating": 2},
#             {"movieId": 3, "my_rating": 2},
#             {"movieId": 4, "my_rating": 2},
#             {"movieId": 5, "my_rating": 2},
#             {"movieId": 6, "my_rating": 2},
#             {"movieId": 7, "my_rating": 2},
#             {"movieId": 8, "my_rating": 2},
#             {"movieId": 9, "my_rating": 2},
#             {"movieId": 10, "my_rating": 2},
#             {"movieId": 11, "my_rating": 2},
#             {"movieId": 12, "my_rating": 6},
#         ]
#     )
#
#     movie_recommendation_list = movie_recommender.get_movie_recommendations()
#     assert len(movie_recommendation_list) == 50
#
#
# def test_receive_50_movie_recommendations_with_2_users():
#     """
#     triggering a special case where the movies are returned from 2 users
#     This happens when the top matched user has rated less than 50 movies (rare case to hit a match)
#     :return:
#     """
#     movie_recommender = MovieRecommender(
#         [
#             {"movieId": 1, "my_rating": 2},
#             {"movieId": 260, "my_rating": 5},
#             {"movieId": 318, "my_rating": 5},
#             {"movieId": 356, "my_rating": 4.5},
#             {"movieId": 480, "my_rating": 4.5},
#             {"movieId": 6, "my_rating": 2},
#             {"movieId": 7, "my_rating": 2},
#             {"movieId": 8, "my_rating": 2},
#             {"movieId": 9, "my_rating": 2},
#             {"movieId": 10, "my_rating": 2},
#             {"movieId": 11, "my_rating": 2},
#             {"movieId": 12, "my_rating": 6},
#         ]
#     )
#
#     movie_recommendation_list = movie_recommender.get_movie_recommendations()
#     assert len(movie_recommendation_list) == 50
