package ch.uzh.ifi.seal.ase.mrs.freeservice.service;

import ch.uzh.ifi.seal.ase.mrs.freeservice.client.TmdbClient;
import ch.uzh.ifi.seal.ase.mrs.freeservice.exception.GeneralWebserviceException;
import ch.uzh.ifi.seal.ase.mrs.freeservice.model.Movie;
import ch.uzh.ifi.seal.ase.mrs.freeservice.model.tmdb.TmdbMovie;
import ch.uzh.ifi.seal.ase.mrs.freeservice.repository.MovieRepository;
import ch.uzh.ifi.seal.ase.mrs.freeservice.service.impl.MovieServiceImpl;
import org.junit.jupiter.api.Assertions;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;


import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.when;

/**
 * Test MovieServiceImplTest
 */
@ExtendWith(MockitoExtension.class)
public class MovieServiceImplTest {


    @Mock
    MovieRepository movieRepository;

    @Mock
    TmdbClient tmdbClient;


    /**
     * Test getMovies
     */
    @Test
    public void testGetMovies() {
        int amount = 30;
        Map<Long, TmdbMovie> tmdbMovieMap = new HashMap<>();
        for (int i = 1; i <= amount; i++) {
            Optional<Movie> movie = Optional.of(new Movie((long) i, ((long) i + 1), "imdbId",5.0));
            lenient().when(movieRepository.findById((long) i)).thenReturn(movie);
            TmdbMovie tmdbMovie = TmdbMovie.builder().id((long) i + 1).title("Title" + i+1).posterPath("path").build();
            tmdbMovieMap.put(tmdbMovie.getId(), tmdbMovie);
        }

        when(movieRepository.count()).thenReturn((long) amount);
        MovieServiceImpl movieService = new MovieServiceImpl(movieRepository, tmdbClient) {
            long i = 0;
            @Override
            public TmdbMovie getTmdbMovieById(Long movieId, boolean includeCast) {
                return tmdbMovieMap.get(movieId);
            }
            // Override the random ID generator, in order to test the get movies method efficiently
            @Override
            public long getRandomMovieId() {
                long result = (i % amount) + 1;
                i++;
                return result;
            }
        };
        final List<TmdbMovie> result = movieService.getMovies(3);

        Assertions.assertEquals(3, result.size());

        final List<TmdbMovie> result2 = movieService.getMovies(2);
        Assertions.assertEquals(2, result2.size());

        Assertions.assertThrows(GeneralWebserviceException.class, () -> {
            final List<TmdbMovie> result3 = movieService.getMovies(20);
        });
    }

    /**
     * Test getMovies with amount smaller than 10 but larger than in db
     */
    @Test
    public void testGetMoviesAmountSmallerThanNumberInDb() {
        int amount = 6;
        Map<Long, TmdbMovie> tmdbMovieMap = new HashMap<>();
        for (int i = 1; i <= amount; i++) {
            Optional<Movie> movie = Optional.of(new Movie((long) i, ((long) i + 1), "imdbId", 5.0));
            lenient().when(movieRepository.findById((long) i)).thenReturn(movie);
            TmdbMovie tmdbMovie = TmdbMovie.builder().id((long) i + 1).title("Title" + i+1).build();
            tmdbMovieMap.put(tmdbMovie.getId(), tmdbMovie);
        }

        when(movieRepository.count()).thenReturn((long) amount);
        MovieServiceImpl movieService = new MovieServiceImpl(movieRepository, tmdbClient) {
            long i = 0;
            @Override
            public TmdbMovie getTmdbMovieById(Long movieId, boolean includeCast) {
                return tmdbMovieMap.get(movieId);
            }
            // Override the random ID generator, in order to test the get movies method efficiently
            @Override
            public long getRandomMovieId() {
                long result = (i % amount) + 1;
                i++;
                return result;
            }
        };
        Assertions.assertThrows(GeneralWebserviceException.class, () -> {
            final List<TmdbMovie> result = movieService.getMovies(8);
        });
    }


    /**
     * Test getMovies alternative paths
     */
    @Test
    public void testGetMoviesBranches() {
        Map<Long, TmdbMovie> tmdbMovieMap = new HashMap<>();
        Optional<Movie> movie1 = Optional.of(new Movie((long) 1, ((long) 2), "imdbId", 5.0));
        Optional<Movie> movie3 = Optional.of(new Movie((long) 3, ((long) 4), "imdbId",5.0));
        Optional<Movie> movie4 = Optional.of(new Movie((long) 4, ((long) 5), "imdbId",5.0));
        TmdbMovie tmdbMovie1 = TmdbMovie.builder().id((long) 2).title("Title 2").posterPath("path").build();
        TmdbMovie tmdbMovie4 = TmdbMovie.builder().id((long) 5).title("Title 5").posterPath("path").build();
        when(movieRepository.findById((long) 1)).thenReturn(movie1);
        when(movieRepository.findById((long) 2)).thenReturn(Optional.empty());
        when(movieRepository.findById((long) 3)).thenReturn(movie3);
        when(movieRepository.findById((long) 4)).thenReturn(movie4);
        tmdbMovieMap.put(2L, tmdbMovie1);
        tmdbMovieMap.put(4L, null);
        tmdbMovieMap.put(5L, tmdbMovie4);
        when(movieRepository.count()).thenReturn((3L));

        MovieServiceImpl movieService = new MovieServiceImpl(movieRepository, tmdbClient) {
            long i = 1;
            @Override
            public TmdbMovie getTmdbMovieById(Long movieId, boolean includeCast) {
                return tmdbMovieMap.get(movieId);
            }
            // Override the random ID generator, in order to test the get movies method efficiently
            @Override
            public long getRandomMovieId() {
                long result = i;
                i++;
                return result;
            }
        };
        final List<TmdbMovie> result = movieService.getMovies(2);

        Assertions.assertEquals(2, result.size());
        Assertions.assertThrows(GeneralWebserviceException.class, () -> {
            movieService.getMovies(9);
        });

    }

    /**
     * Test getMovies with no poster path
     */
    @Test
    public void testGetMoviesNoPosterPath() {
        Map<Long, TmdbMovie> tmdbMovieMap = new HashMap<>();
        Optional<Movie> movie1 = Optional.of(new Movie((long) 1, ((long) 1), "imdbId", 5.0));
        Optional<Movie> movie2 = Optional.of(new Movie((long) 2, ((long) 2), "imdbId",5.0));
        TmdbMovie tmdbMovie1 = TmdbMovie.builder().id((long) 1).title("Title 1").posterPath("path").build();
        TmdbMovie tmdbMovie2 = TmdbMovie.builder().id((long) 2).title("Title 2").posterPath(null).build();
        when(movieRepository.findById((long) 1)).thenReturn(movie1);
        when(movieRepository.findById((long) 2)).thenReturn(movie2);
        tmdbMovieMap.put(1L, tmdbMovie1);
        tmdbMovieMap.put(2L, tmdbMovie2);
        when(movieRepository.count()).thenReturn((2L));

        MovieServiceImpl movieService = new MovieServiceImpl(movieRepository, tmdbClient) {
            long i = 1;
            @Override
            public TmdbMovie getTmdbMovieById(Long movieId, boolean includeCast) {
                return tmdbMovieMap.get(movieId);
            }
            // Override the random ID generator, in order to test the get movies method efficiently
            @Override
            public long getRandomMovieId() {
                long result = i;
                i++;
                return result;
            }
        };
        final List<TmdbMovie> result = movieService.getMovies(1);

        Assertions.assertEquals(1, result.size());
        Assertions.assertThrows(GeneralWebserviceException.class, () -> {
            movieService.getMovies(2);
        });
    }

    /**
     * Test if random Id is within range
     */
    @Test
    public void testGetRandomMovieId() {
        when(movieRepository.count()).thenReturn((5L));

        MovieServiceImpl movieService = new MovieServiceImpl(movieRepository, tmdbClient);

        for (int i = 0; i < 50; i++) {
            long randomId = movieService.getRandomMovieId();
            Assertions.assertTrue(randomId >= 1 && randomId <= 5);
        }

    }
}
