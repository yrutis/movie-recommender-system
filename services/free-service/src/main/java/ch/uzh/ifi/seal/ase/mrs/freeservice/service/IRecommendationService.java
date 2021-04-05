package ch.uzh.ifi.seal.ase.mrs.freeservice.service;

import ch.uzh.ifi.seal.ase.mrs.freeservice.model.ActorRating;
import ch.uzh.ifi.seal.ase.mrs.freeservice.model.MovieRating;
import ch.uzh.ifi.seal.ase.mrs.freeservice.model.tmdb.TmdbMovie;

import java.util.List;

/**
 * Interface of Recommendations Service
 */
public interface IRecommendationService {

    /**
     * Gets recommendations from the inference backend
     * @param movieRatings List of rated movies
     * @param actorRatings List of rated actors
     * @return List of movie recommendations
     */
    List<TmdbMovie> getRecommendations(List<MovieRating> movieRatings, List<ActorRating> actorRatings);

}
