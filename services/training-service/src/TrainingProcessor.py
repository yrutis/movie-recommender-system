from threading import Thread

from src.recommendations.movie_recommender import MovieRecommender


class TrainingProcessor(Thread):
    def __init__(self):
        super(TrainingProcessor, self).__init__()

    def run(self):
        movie_recommender = MovieRecommender(use_db=1)
        movies = movie_recommender.train_movie_recommendations()