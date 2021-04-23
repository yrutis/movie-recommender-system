package ch.uzh.ifi.seal.ase.mrs.freeservice.service;

import ch.uzh.ifi.seal.ase.mrs.freeservice.model.Movie;
import ch.uzh.ifi.seal.ase.mrs.freeservice.model.tmdb.TmdbMovie;

import java.util.List;

/**
 * Interface of Movie Service
 */
public interface IMovieService {
    /**
     * Collects movies until there are the amount of movies asked in the set
     * @param amount amount of movies
     * @return set of movies as list
     */
    List<TmdbMovie> getMovies(Integer amount);

    /**
     * Get TmdbMovie by Id
     * @param movieId movie id
     * @return Tmdb Movie
     */
    TmdbMovie getTmdbMovieById(Long movieId);

}
