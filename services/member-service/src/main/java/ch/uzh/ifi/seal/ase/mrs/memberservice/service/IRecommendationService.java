package ch.uzh.ifi.seal.ase.mrs.memberservice.service;

import ch.uzh.ifi.seal.ase.mrs.memberservice.model.tmdb.TmdbMovie;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Interface of Recommendation Service
 */
public interface IRecommendationService {

    /**
     * Get movie recommendations for a user
     *
     * @param principal the user that wants recommendations
     * @return List of movie recommendations
     */
    List<TmdbMovie> getRecommendations(Principal principal);

    /**
     * Get last trainind date of a user
     *
     * @param principal the user that wants the last training date
     * @return last training date
     */
    LocalDateTime getLastTrained(Principal principal);

    /**
     * Get TmdbMovie by Id
     *
     * @param movieId movie id
     * @return TmdbMovie
     */
    TmdbMovie getTmdbMovieById(Long movieId);
}
