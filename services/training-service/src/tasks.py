import os

from celery import Celery

from src.recommendations.movie_recommender import MovieRecommender

celery = Celery(__name__)
celery.conf.broker_url = os.environ.get("CELERY_BROKER_URL", "redis://localhost:6379")
celery.conf.result_backend = os.environ.get(
    "CELERY_RESULT_BACKEND", "redis://localhost:6379"
)


@celery.task(name="create_task", track_started=True)
def create_task():
    movie_recommender = MovieRecommender(use_db=1)
    movies = movie_recommender.train_movie_recommendations()
    return movies
