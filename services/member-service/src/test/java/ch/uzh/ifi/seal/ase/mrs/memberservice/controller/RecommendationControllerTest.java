package ch.uzh.ifi.seal.ase.mrs.memberservice.controller;

import ch.uzh.ifi.seal.ase.mrs.memberservice.client.TmdbClient;
import ch.uzh.ifi.seal.ase.mrs.memberservice.client.TrainingClient;
import ch.uzh.ifi.seal.ase.mrs.memberservice.exception.GeneralWebserviceException;
import ch.uzh.ifi.seal.ase.mrs.memberservice.model.tmdb.TmdbMovie;
import ch.uzh.ifi.seal.ase.mrs.memberservice.service.impl.RecommendationServiceImpl;
import feign.FeignException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;

/**
 * Test RecommendationController
 */
@ExtendWith(MockitoExtension.class)
public class RecommendationControllerTest {
    @InjectMocks
    RecommendationController recommendationController;

    @Mock
    RecommendationServiceImpl recommendationService;

    @Mock
    FeignException.FeignClientException exception;

    @Mock
    TrainingClient trainingClient;

    @Mock
    Principal principal;


    /**
     * Test getRecommendations
     */
    @Test
    public void testGetRecommendations() {
        TmdbMovie tmdbMovie1 = TmdbMovie.builder().id(1L).title("Movie1").build();
        TmdbMovie tmdbMovie2 = TmdbMovie.builder().id(2L).title("Movie2").build();
        when(recommendationService.getRecommendations(Mockito.any())).thenReturn(Arrays.asList(tmdbMovie1, tmdbMovie2));
        List<TmdbMovie> recommendations = recommendationController.getRecommendations(principal);
        Assertions.assertEquals(2, recommendations.size());
        Assertions.assertEquals(1L, recommendations.get(0).getId());
    }

    /**
     * Test getLastTrained
     */
    @Test
    public void testGetLastTrained() {
        LocalDateTime localDateTime = LocalDateTime.now();
        when(recommendationService.getLastTrained(Mockito.any())).thenReturn(localDateTime);
        LocalDateTime result = recommendationController.getLastTrained(principal);
        Assertions.assertEquals(localDateTime, result);
    }

    /**
     * Test train
     */
    @Test
    public void testTrain() {
        doNothing().when(trainingClient).startTraining();
        recommendationController.train();
    }

    /**
     * Test train Exception
     */
    @Test
    public void testTrainException() {
        doThrow(exception).when(trainingClient).startTraining();
        Assertions.assertThrows(GeneralWebserviceException.class, () -> {
            recommendationController.train();
        });
    }

}
