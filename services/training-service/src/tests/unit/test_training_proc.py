from src.training_processor.TrainingProcessor import TrainingProcessor

def test_training_processor(test_app):
    training_processor = TrainingProcessor()
    training_processor.run()