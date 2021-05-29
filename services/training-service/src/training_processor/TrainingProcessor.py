from threading import Thread

from src.recommendations.movie_recommender import MovieRecommender


class TrainingProcessor(Thread):
    """
    This class exists to run the training process in a separate process.
    This way, the api request can be handled async
    """

    def __init__(self):
        """
        only init obligatory variables in the parents class
        """
        super(TrainingProcessor, self).__init__()

    def run(self):
        """
        run the actual training
        """
        movie_recommender = MovieRecommender()
        movie_recommender.train_movie_recommendations()
