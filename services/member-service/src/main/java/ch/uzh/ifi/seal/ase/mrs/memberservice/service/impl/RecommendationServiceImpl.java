package ch.uzh.ifi.seal.ase.mrs.memberservice.service.impl;

import ch.uzh.ifi.seal.ase.mrs.memberservice.client.TmdbClient;
import ch.uzh.ifi.seal.ase.mrs.memberservice.exception.GeneralWebserviceException;
import ch.uzh.ifi.seal.ase.mrs.memberservice.model.MovieRating;
import ch.uzh.ifi.seal.ase.mrs.memberservice.model.User;
import ch.uzh.ifi.seal.ase.mrs.memberservice.model.tmdb.TmdbCast;
import ch.uzh.ifi.seal.ase.mrs.memberservice.model.tmdb.TmdbMovie;
import ch.uzh.ifi.seal.ase.mrs.memberservice.model.tmdb.TmdbWatchProvider;
import ch.uzh.ifi.seal.ase.mrs.memberservice.model.tmdb.TmdbWatchProviderCountry;
import ch.uzh.ifi.seal.ase.mrs.memberservice.repository.RatingRepository;
import ch.uzh.ifi.seal.ase.mrs.memberservice.repository.UserRepository;
import ch.uzh.ifi.seal.ase.mrs.memberservice.service.IRecommendationService;
import feign.FeignException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.TreeSet;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.collectingAndThen;
import static java.util.stream.Collectors.toCollection;

@Service
@Slf4j
@CacheConfig(cacheNames={"recommendations"})
public class RecommendationServiceImpl implements IRecommendationService {

    private final UserRepository userRepository;
    private final RatingRepository ratingRepository;
    private final TmdbClient tmdbClient;

    @Value("${tmdb.api-key}")
    private String tmdbApiKey;

    @Value("${tmdb.watch-provider-locale}")
    private String tmdbWatchProviderLocale;

    /**
     * Constructor, autowires user repository and rating repository
     * @param userRepository  user repository
     * @param ratingRepository rating repository
     * @param tmdbClient The movie Database client
     */
    public RecommendationServiceImpl(UserRepository userRepository, RatingRepository ratingRepository, TmdbClient tmdbClient) {
        this.userRepository = userRepository;
        this.ratingRepository = ratingRepository;
        this.tmdbClient = tmdbClient;
    }

    /**
     * Get movie recommendations for a user
     *
     * @param principal the user that wants recommendations
     * @return List of movie recommendations
     */
    @Override
    public List<TmdbMovie> getRecommendations(Principal principal) {
        User user = userRepository.findByUsername(principal.getName()).orElseThrow(() -> GeneralWebserviceException.builder().errorCode("U001").message("Username not found").status(HttpStatus.NOT_FOUND).build());
        List<MovieRating> movieRatings = ratingRepository.findAllByUserId(user.getTblRatingUserId());
        return movieRatings.stream().collect(collectingAndThen(toCollection(() -> new TreeSet<>(Comparator.comparingLong(MovieRating::getTmdbId))), ArrayList::new)).stream().map(rating -> getTmdbMovieById(rating.getTmdbId())).filter(tmdbMovie -> tmdbMovie != null && tmdbMovie.getPosterPath() != null).sorted(Comparator.comparingDouble(TmdbMovie::getPopularity).reversed()).collect(Collectors.toList());
    }

    /**
     * Get last trainind date of a user
     *
     * @param principal the user that wants the last training date
     * @return last training date
     */
    @Override
    public LocalDateTime getLastTrained(Principal principal) {
        User user = userRepository.findByUsername(principal.getName()).orElseThrow(() -> GeneralWebserviceException.builder().errorCode("U001").message("Username not found").status(HttpStatus.NOT_FOUND).build());
        return user.getLastTrainedOn();
    }

    /**
     * Get TmdbMovie by Id
     *
     * @param movieId movie id
     * @return TmdbMovie
     */
    @Override
    @Cacheable
    public TmdbMovie getTmdbMovieById(Long movieId) {
        if(tmdbApiKey == null){
            throw GeneralWebserviceException.builder().errorCode("TMDB001").status(HttpStatus.INTERNAL_SERVER_ERROR).message("API KEY NOT SET").build();
        }
        try {
            TmdbMovie tmdbMovie = this.tmdbClient.getMovie(movieId.toString(), tmdbApiKey);
            tmdbMovie.setCast(getCast(movieId));
            tmdbMovie.setWatchProviders(getWatchProviders(movieId));
            return tmdbMovie;
        } catch (FeignException exception) {
            log.info("Problem with Feign Client or the API, e.g. Movie Not found. TmdbID: " + movieId.toString());
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
}
