package ch.uzh.ifi.seal.ase.mrs.freeservice.controller;

import ch.uzh.ifi.seal.ase.mrs.freeservice.model.ActorRating;
import ch.uzh.ifi.seal.ase.mrs.freeservice.model.MovieRating;
import ch.uzh.ifi.seal.ase.mrs.freeservice.model.RatingDto;
import ch.uzh.ifi.seal.ase.mrs.freeservice.model.tmdb.TmdbMovie;
import ch.uzh.ifi.seal.ase.mrs.freeservice.service.IMovieService;
import ch.uzh.ifi.seal.ase.mrs.freeservice.service.IRecommendationService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.when;

/**
 * Test RecommendationController
 */
@ExtendWith(MockitoExtension.class)
public class RecommendationControllerTest {

    @InjectMocks
    RecommendationController recommendationController;

    @Mock
    IRecommendationService recommendationService;

    /**
     * Test get Movies
     */
    @Test
    public void testGetRecommendations() {
        TmdbMovie movie1 = TmdbMovie.builder().id(1L).title("movie1").build();
        TmdbMovie movie2 = TmdbMovie.builder().id(2L).title("movie2").build();
        List<TmdbMovie> movies = Arrays.asList(movie1, movie2);
        RatingDto ratingDto = RatingDto.builder().movieRatings(Arrays.asList(MovieRating.builder().rating(4.5).tmdbId(7L).build())).actorRatings(Arrays.asList(ActorRating.builder().rating(4.5).tmdbId(7L).build())).build();
        when(recommendationService.getRecommendations(ratingDto.getMovieRatings(), ratingDto.getActorRatings())).thenReturn(movies);
        List<TmdbMovie> result = recommendationController.getRecommendations(ratingDto);
        Assertions.assertEquals(2, result.size());
        Assertions.assertEquals("movie1", result.get(0).getTitle());
    }
}
