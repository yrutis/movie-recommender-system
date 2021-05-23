package ch.uzh.ifi.seal.ase.mrs.freeservice.service.impl;

import ch.uzh.ifi.seal.ase.mrs.freeservice.client.TmdbClient;
import ch.uzh.ifi.seal.ase.mrs.freeservice.exception.GeneralWebserviceException;
import ch.uzh.ifi.seal.ase.mrs.freeservice.model.Movie;
import ch.uzh.ifi.seal.ase.mrs.freeservice.model.tmdb.*;
import ch.uzh.ifi.seal.ase.mrs.freeservice.repository.MovieRepository;
import ch.uzh.ifi.seal.ase.mrs.freeservice.service.IMovieService;
import feign.FeignException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Implementation of IMovieService
 */
@Service
@Slf4j
@CacheConfig(cacheNames={"movies"})
public class MovieServiceImpl implements IMovieService {
    private final MovieRepository movieRepository;
    private final TmdbClient tmdbClient;
    private final Random random;
    private final long numberOfMoviesInDatabase;

    @Value("${tmdb.api-key}")
    private String tmdbApiKey;

    @Value("${tmdb.watch-provider-locale}")
    private String tmdbWatchProviderLocale;

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
    }

    /**
     * Collects movies until there are the amount of movies asked in the set
     *
     * @param amount amount of movies
     * @return set of movies as list
     */
    @Override
    public List<TmdbMovie> getMovies(Integer amount, Integer popularity) {
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
            double lambda = (1 / (double) this.numberOfMoviesInDatabase) * (popularity * 50);
            Optional<Movie> movie = movieRepository.findById(getRandomMovieId(lambda));
            if (movie.isPresent()) {
                TmdbMovie tmdbMovie = getTmdbMovieById(movie.get().getTmdbId(), false);
                if (tmdbMovie != null && tmdbMovie.getPosterPath() != null) {
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
    @Cacheable
    public TmdbMovie getTmdbMovieById(Long movieId, boolean includeDetails) {
        if(tmdbApiKey == null){
            throw GeneralWebserviceException.builder().errorCode("TMDB001").status(HttpStatus.INTERNAL_SERVER_ERROR).message("API KEY NOT SET").build();
        }
        try {
            TmdbMovie movie =  this.tmdbClient.getMovie(movieId.toString(), tmdbApiKey);
            if(includeDetails){
                movie.setCast(getCast(movieId));
                movie.setWatchProviders(getWatchProviders(movieId));
            }
            return movie;
        } catch (FeignException exception) {
            log.info("Problem with Feign Client or the API, e.g. Movie or Cast Not found. TmdbID: " + movieId.toString());
            return null;
        }
    }

    /**
     * Gets the cast of a movie
     * @param movieId movie ID
     * @return Movie Cast
     */
    private TmdbCast getCast(Long movieId){
        TmdbCast tmdbCast = this.tmdbClient.getCast(movieId.toString(), tmdbApiKey);
        tmdbCast.setCast(tmdbCast.getCast().stream().limit(3).collect(Collectors.toList()));
        return tmdbCast;
    }

    /**
     * Gets the watch providers of a movie
     * @param movieId movie ID
     * @return Watch Providers
     */
    private TmdbWatchProviderCountry getWatchProviders(Long movieId){
        TmdbWatchProvider tmdbWatchProvider = this.tmdbClient.getWatchProvider(movieId.toString(), tmdbApiKey);
        return tmdbWatchProvider.getResults().get(tmdbWatchProviderLocale);
    }


    /**
     * generates a random movie id based on negative exponential distribution
     *
     * @return movie Id
     */
    public long getRandomMovieId(double lambda) {
        double l = 1;
        long movieId = (long) (-Math.log(Math.exp(-lambda * l) - (Math.exp(-lambda * l) - Math.exp(-lambda * this.numberOfMoviesInDatabase)) * this.random.nextDouble()) / lambda);
        if (movieId >= 1 && movieId <= this.numberOfMoviesInDatabase) {
            return movieId;
        }
        // return 1 if the generated number is not in the range due to some conversion errors, should not happen at all
        return 1;
    }
}
