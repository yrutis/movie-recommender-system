package ch.uzh.ifi.seal.ase.mrs.memberservice.service.impl;

import ch.uzh.ifi.seal.ase.mrs.memberservice.client.TmdbClient;
import ch.uzh.ifi.seal.ase.mrs.memberservice.exception.GeneralWebserviceException;
import ch.uzh.ifi.seal.ase.mrs.memberservice.model.MovieRating;
import ch.uzh.ifi.seal.ase.mrs.memberservice.model.User;
import ch.uzh.ifi.seal.ase.mrs.memberservice.model.tmdb.TmdbMovie;
import ch.uzh.ifi.seal.ase.mrs.memberservice.repository.RatingRepository;
import ch.uzh.ifi.seal.ase.mrs.memberservice.repository.UserRepository;
import ch.uzh.ifi.seal.ase.mrs.memberservice.service.IRecommendationService;
import feign.FeignException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class RecommendationServiceImpl implements IRecommendationService {

    private final UserRepository userRepository;
    private final RatingRepository ratingRepository;
    private final TmdbClient tmdbClient;

    @Value("${tmdb.api-key}")
    private String tmdbApiKey;

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
        return movieRatings.stream().map(rating -> getTmdbMovieById(rating.getTmdbId())).filter(tmdbMovie -> tmdbMovie != null && tmdbMovie.getPosterPath() != null).sorted(Comparator.comparingDouble(TmdbMovie::getPopularity).reversed()).collect(Collectors.toList());
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
    public TmdbMovie getTmdbMovieById(Long movieId) {
        if(tmdbApiKey == null){
            throw GeneralWebserviceException.builder().errorCode("TMDB001").status(HttpStatus.INTERNAL_SERVER_ERROR).message("API KEY NOT SET").build();
        }
        try {
            return this.tmdbClient.getMovie(movieId.toString(), tmdbApiKey);
        } catch (FeignException exception) {
            log.info("Problem with Feign Client or the API, e.g. Movie Not found. TmdbID: " + movieId.toString());
            return null;
        }
    }
}
