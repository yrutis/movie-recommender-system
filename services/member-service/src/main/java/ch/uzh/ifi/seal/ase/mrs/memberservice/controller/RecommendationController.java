package ch.uzh.ifi.seal.ase.mrs.memberservice.controller;

import ch.uzh.ifi.seal.ase.mrs.memberservice.client.TrainingClient;
import ch.uzh.ifi.seal.ase.mrs.memberservice.exception.GeneralWebserviceException;
import ch.uzh.ifi.seal.ase.mrs.memberservice.model.tmdb.TmdbMovie;
import ch.uzh.ifi.seal.ase.mrs.memberservice.service.IRecommendationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Rest Controller which handles recommendations for members
 */
@RestController
@RequestMapping("api/recommendations")
@CrossOrigin(origins = "${hosts.gui}")
@Slf4j
public class RecommendationController {

    private final IRecommendationService recommendationService;
    private final TrainingClient trainingClient;

    /**
     * Constructor, autowires the recommendation Service and the training client
     * @param recommendationService Recommendation Service
     * @param trainingClient Training Client
     */
    @Autowired
    public RecommendationController(IRecommendationService recommendationService, TrainingClient trainingClient) {
        this.recommendationService = recommendationService;
        this.trainingClient = trainingClient;

    }

    /**
     * Get movie recommendations for a user
     *
     * @param principal the user that wants recommendations
     * @return List of movie recommendations
     */
    @GetMapping()
    public List<TmdbMovie> getRecommendations(Principal principal) {
        return recommendationService.getRecommendations(principal);
    }

    /**
     * Get last trainind date of a user
     *
     * @param principal the user that wants the last training date
     * @return last training date
     */
    @GetMapping("/lastTrained")
    public LocalDateTime getLastTrained(Principal principal) {
        return recommendationService.getLastTrained(principal);
    }

    /**
     * Starts the training if allowed
     * Also starts the training scheduled every day at 2am
     */
    @GetMapping("/train")
    @Scheduled(cron = "0 0 2 * * *")
    public void train() {
        try {
            trainingClient.startTraining();
        }catch (Exception e) {
            throw GeneralWebserviceException.builder().message("Training could not be started").errorCode("T001").status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
