package ch.uzh.ifi.seal.ase.mrs.freeservice.service.impl;

import ch.uzh.ifi.seal.ase.mrs.freeservice.client.TmdbClient;
import ch.uzh.ifi.seal.ase.mrs.freeservice.exception.GeneralWebserviceException;
import ch.uzh.ifi.seal.ase.mrs.freeservice.model.Movie;
import ch.uzh.ifi.seal.ase.mrs.freeservice.model.tmdb.TmdbMovie;
import ch.uzh.ifi.seal.ase.mrs.freeservice.repository.MovieRepository;
import ch.uzh.ifi.seal.ase.mrs.freeservice.service.IMovieService;
import feign.FeignException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Implementation of IMovieService
 */
@Service
@Slf4j
public class MovieServiceImpl implements IMovieService {
    private final MovieRepository movieRepository;
    private final TmdbClient tmdbClient;
    private final Random random;
    private final long numberOfMoviesInDatabase;
    private final double lambda;

    @Value("${tmdb.api-key}")
    private String tmdbApiKey;

    /**
     * Constructor, autowires the MovieRepository and the TmdbClient, creates a Random Object, and counts the number of movies in the database
     *
     * @param movieRepository movie repository
     * @param tmdbClient      tmdbClient
     */
    @Autowired
    public MovieServiceImpl(MovieRepository movieRepository, TmdbClient tmdbClient) {
        this.movieRepository = movieRepository;
        this.tmdbClient = tmdbClient;
        this.random = new Random();
        this.numberOfMoviesInDatabase = this.movieRepository.count();
        this.lambda = (1 / (double) this.numberOfMoviesInDatabase) * 100;
    }

    /**
     * Collects movies until there are the amount of movies asked in the set
     *
     * @param amount amount of movies
     * @return set of movies as list
     */
    @Override
    public List<TmdbMovie> getMovies(Integer amount) {
        if (amount > numberOfMoviesInDatabase || amount > 10) {
            throw GeneralWebserviceException.builder().status(HttpStatus.BAD_REQUEST).errorCode("M001").message("Maximum number of movies per request: 10").build();
        }

        Set<TmdbMovie> movies = new HashSet<>();
        int maxAttempts = amount * 10;
        int currentIter = 0;
        while (movies.size() < amount) {
            currentIter++;
            if (currentIter >= maxAttempts) {
                throw GeneralWebserviceException.builder().status(HttpStatus.INTERNAL_SERVER_ERROR).errorCode("M002").message("Maximum number of retries reached without finding enough unique movies").build();
            }
            Optional<Movie> movie = movieRepository.findById(getRandomMovieId());
            if (movie.isPresent()) {
                TmdbMovie tmdbMovie = getTmdbMovieById(movie.get().getTmdbId());
                if (tmdbMovie != null) {
                    movies.add(tmdbMovie);
                }
            }
        }

        return new ArrayList<>(movies);
    }

    /**
     * Get TmdbMovie by Id
     *
     * @param movieId movie id
     * @return TmdbMovie
     */
    @Override
    public TmdbMovie getTmdbMovieById(Long movieId) {
        try {
            return this.tmdbClient.getMovie(movieId.toString(), tmdbApiKey);
        } catch (FeignException exception) {
            log.info("Problem with Feign Client or the API, e.g. Movie Not found. TmdbID: " + movieId.toString());
            return null;
        }
    }

    /**
     * generates a random movie id based on negative exponential distribution
     *
     * @return movie Id
     */
    public long getRandomMovieId() {
        double l = 1;
        long movieId = (long) (-Math.log(Math.exp(-this.lambda * l) - (Math.exp(-this.lambda * l) - Math.exp(-this.lambda * this.numberOfMoviesInDatabase)) * this.random.nextDouble()) / this.lambda);
        if (movieId >= 1 && movieId <= this.numberOfMoviesInDatabase) {
            return movieId;
        }
        // return 1 if the generated number is not in the range due to some conversion errors, should not happen at all
        return 1;
    }
}
