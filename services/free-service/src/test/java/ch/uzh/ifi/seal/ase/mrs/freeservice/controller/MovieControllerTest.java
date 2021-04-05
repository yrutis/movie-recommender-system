package ch.uzh.ifi.seal.ase.mrs.freeservice.controller;

import ch.uzh.ifi.seal.ase.mrs.freeservice.model.Movie;
import ch.uzh.ifi.seal.ase.mrs.freeservice.model.tmdb.TmdbMovie;
import ch.uzh.ifi.seal.ase.mrs.freeservice.service.IMovieService;
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
 * Test MovieController
 */
@ExtendWith(MockitoExtension.class)
public class MovieControllerTest {

    @InjectMocks
    MovieController movieController;

    @Mock
    IMovieService movieService;

    /**
     * Test get Movies
     */
    @Test
    public void testGetMovies() {
        TmdbMovie movie1 = TmdbMovie.builder().id(1L).title("movie1").build();
        TmdbMovie movie2 = TmdbMovie.builder().id(2L).title("movie2").build();
        List<TmdbMovie> movies = Arrays.asList(movie1, movie2);
        when(movieService.getMovies(2)).thenReturn(movies);
        List<TmdbMovie> result = movieController.getMovies(2);
        Assertions.assertEquals(2, result.size());
        Assertions.assertEquals("movie1", result.get(0).getTitle());
    }
}
