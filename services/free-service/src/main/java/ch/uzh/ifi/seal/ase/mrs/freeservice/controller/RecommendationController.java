package ch.uzh.ifi.seal.ase.mrs.freeservice.controller;

import ch.uzh.ifi.seal.ase.mrs.freeservice.model.RatingDto;
import ch.uzh.ifi.seal.ase.mrs.freeservice.model.tmdb.TmdbMovie;
import ch.uzh.ifi.seal.ase.mrs.freeservice.service.IRecommendationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Rest Controller which gets recommendations
 */
@RestController
@RequestMapping("api/recommendations")
@CrossOrigin(origins = "${hosts.gui}")
public class RecommendationController {

    private final IRecommendationService recommendationService;

    /**
     * Constructor, autowires the recommendation service
     *
     * @param recommendationService movie service
     */
    @Autowired
    public RecommendationController(IRecommendationService recommendationService) {
        this.recommendationService = recommendationService;
    }


    /**
     * Get movie recommendations based on ratings
     *
     * @param ratings list of actor ratings and list of movie ratings wrapped in a RatingDto
     * @return List of movie recommendations
     */
    @PostMapping()
    public List<TmdbMovie> getRecommendations(@RequestBody RatingDto ratings) {
        return recommendationService.getRecommendations(ratings.getMovieRatings(), ratings.getActorRatings());
    }
}
