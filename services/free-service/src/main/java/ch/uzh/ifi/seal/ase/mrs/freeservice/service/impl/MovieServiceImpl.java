package ch.uzh.ifi.seal.ase.mrs.freeservice.service.impl;

import ch.uzh.ifi.seal.ase.mrs.freeservice.client.TmdbClient;
import ch.uzh.ifi.seal.ase.mrs.freeservice.exception.GeneralWebserviceException;
import ch.uzh.ifi.seal.ase.mrs.freeservice.model.Movie;
import ch.uzh.ifi.seal.ase.mrs.freeservice.model.tmdb.TmdbMovie;
import ch.uzh.ifi.seal.ase.mrs.freeservice.repository.MovieRepository;
import ch.uzh.ifi.seal.ase.mrs.freeservice.service.IMovieService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.*;

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
     * @param tmdbClient tmdbClient
     */
    @Autowired
    public MovieServiceImpl(MovieRepository movieRepository, TmdbClient tmdbClient) {
        this.movieRepository = movieRepository;
        this.tmdbClient = tmdbClient;
        this.random = new Random();
        this.numberOfMoviesInDatabase = this.movieRepository.count();
        this.lambda = (1 / (double) this.numberOfMoviesInDatabase) * 15;
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
        while (movies.size() < amount) {
            Optional<Movie> movie = movieRepository.findById(getRandomMovieId());
            if(movie.isPresent()){
                TmdbMovie tmdbMovie = getTmdbMovie(movie.get().getTmdbId().toString());
                if (tmdbMovie != null) {
                    movies.add(tmdbMovie);
                }
            }
        }

        return new ArrayList<>(movies);
    }

    /**
     * Wrapper method since feign clients cannot be testet with Mockito when
     */

    public TmdbMovie getTmdbMovie(String movieId) {
        return this.tmdbClient.getMovie(movieId, tmdbApiKey);
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
