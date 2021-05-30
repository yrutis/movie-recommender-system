package ch.uzh.ifi.seal.ase.mrs.freeservice.service;

import ch.uzh.ifi.seal.ase.mrs.freeservice.client.InferenceClient;
import ch.uzh.ifi.seal.ase.mrs.freeservice.client.TmdbClient;
import ch.uzh.ifi.seal.ase.mrs.freeservice.exception.GeneralWebserviceException;
import ch.uzh.ifi.seal.ase.mrs.freeservice.model.Actor;
import ch.uzh.ifi.seal.ase.mrs.freeservice.model.ActorRating;
import ch.uzh.ifi.seal.ase.mrs.freeservice.model.MovieRating;
import ch.uzh.ifi.seal.ase.mrs.freeservice.model.tmdb.TmdbActor;
import ch.uzh.ifi.seal.ase.mrs.freeservice.model.tmdb.TmdbMovie;
import ch.uzh.ifi.seal.ase.mrs.freeservice.repository.ActorRepository;
import ch.uzh.ifi.seal.ase.mrs.freeservice.service.impl.ActorServiceImpl;
import ch.uzh.ifi.seal.ase.mrs.freeservice.service.impl.RecommendationServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.when;

/**
 * Test RecommendationServiceImplTest
 */
@ExtendWith(MockitoExtension.class)
public class RecommendationServiceImplTest {


    @Mock
    ActorRepository actorRepository;

    @Mock
    IMovieService movieService;

    @Mock
    InferenceClient inferenceClient;

    /**
     * Test getActors
     */
    @Test
    public void testGetRecommendations() {
        Actor actor1 = Actor.builder().id(1L).tmdbId(1L).top1Movie(1L).top2Movie(2L).top3Movie(3L).build();
        Actor actor2 = Actor.builder().id(2L).tmdbId(2L).top1Movie(1L).top2Movie(2L).top3Movie(3L).build();
        Actor actor3 = Actor.builder().id(3L).tmdbId(3L).top1Movie(1L).top2Movie(2L).top3Movie(3L).build();
        when(actorRepository.findByTmdbId(1L)).thenReturn(Optional.of(actor1));
        when(actorRepository.findByTmdbId(2L)).thenReturn(Optional.of(actor2));
        when(actorRepository.findByTmdbId(3L)).thenReturn(Optional.of(actor3));
        MovieRating m1 = MovieRating.builder().rating(4.0).tmdbId(1L).build();
        MovieRating m2 = MovieRating.builder().rating(3.0).tmdbId(2L).build();
        MovieRating m3 = MovieRating.builder().rating(4.0).tmdbId(3L).build();
        ActorRating a1 = ActorRating.builder().rating(4.0).tmdbId(1L).build();
        ActorRating a2 = ActorRating.builder().rating(4.0).tmdbId(2L).build();
        ActorRating a3 = ActorRating.builder().rating(4.0).tmdbId(3L).build();
        when(inferenceClient.getRecommendations(ArgumentMatchers.any())).thenReturn(Arrays.asList(5L, 6L, 7L));
        TmdbMovie tmdbMovie1 = TmdbMovie.builder().id(5L).posterPath("path").popularity(5.0).build();
        TmdbMovie tmdbMovie2 = TmdbMovie.builder().id(6L).posterPath("path").popularity(2.0).build();
        TmdbMovie tmdbMovie3 = TmdbMovie.builder().id(7L).build();
        when(movieService.getTmdbMovieById(5L, true)).thenReturn(tmdbMovie1);
        when(movieService.getTmdbMovieById(6L, true)).thenReturn(tmdbMovie2);
        when(movieService.getTmdbMovieById(7L, true)).thenReturn(tmdbMovie3);
        RecommendationServiceImpl recommendationService = new RecommendationServiceImpl(actorRepository, inferenceClient, movieService);
        List<MovieRating> movieRatings = new ArrayList<>(Arrays.asList(m1, m2, m3));
        List<ActorRating> actorRatings = new ArrayList<>(Arrays.asList(a1,a2,a3));
        List<TmdbMovie> result = recommendationService.getRecommendations(movieRatings, actorRatings);
        Assertions.assertEquals(2, result.size());
    }

}
