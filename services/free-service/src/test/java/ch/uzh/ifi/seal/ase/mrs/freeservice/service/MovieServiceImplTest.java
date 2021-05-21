package ch.uzh.ifi.seal.ase.mrs.freeservice.service;

import ch.uzh.ifi.seal.ase.mrs.freeservice.client.TmdbClient;
import ch.uzh.ifi.seal.ase.mrs.freeservice.exception.GeneralWebserviceException;
import ch.uzh.ifi.seal.ase.mrs.freeservice.model.Movie;
import ch.uzh.ifi.seal.ase.mrs.freeservice.model.tmdb.*;
import ch.uzh.ifi.seal.ase.mrs.freeservice.repository.MovieRepository;
import ch.uzh.ifi.seal.ase.mrs.freeservice.service.impl.MovieServiceImpl;
import feign.FeignException;
import org.junit.jupiter.api.Assertions;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;


import java.util.*;


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

    @InjectMocks
    MovieServiceImpl movieService;

    @Mock
    FeignException.FeignClientException exception;

    @BeforeEach
    public void setUp() {
        ReflectionTestUtils.setField(movieService, "tmdbApiKey", "value");
        ReflectionTestUtils.setField(movieService, "tmdbWatchProviderLocale", "CH");
    }

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
            public TmdbMovie getTmdbMovieById(Long movieId, boolean includeDetails) {
                return tmdbMovieMap.get(movieId);
            }
            // Override the random ID generator, in order to test the get movies method efficiently
            @Override
            public long getRandomMovieId(double lamda) {
                long result = (i % amount) + 1;
                i++;
                return result;
            }
        };
        final List<TmdbMovie> result = movieService.getMovies(3, 100);

        Assertions.assertEquals(3, result.size());

        final List<TmdbMovie> result2 = movieService.getMovies(2, 100);
        Assertions.assertEquals(2, result2.size());

        Assertions.assertThrows(GeneralWebserviceException.class, () -> {
            final List<TmdbMovie> result3 = movieService.getMovies(20, 100);
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
            public TmdbMovie getTmdbMovieById(Long movieId, boolean includeDetails) {
                return tmdbMovieMap.get(movieId);
            }
            // Override the random ID generator, in order to test the get movies method efficiently
            @Override
            public long getRandomMovieId(double lamda) {
                long result = (i % amount) + 1;
                i++;
                return result;
            }
        };
        Assertions.assertThrows(GeneralWebserviceException.class, () -> {
            final List<TmdbMovie> result = movieService.getMovies(8, 100);
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
            public TmdbMovie getTmdbMovieById(Long movieId, boolean includeDetails) {
                return tmdbMovieMap.get(movieId);
            }
            // Override the random ID generator, in order to test the get movies method efficiently
            @Override
            public long getRandomMovieId(double lamda) {
                long result = i;
                i++;
                return result;
            }
        };
        final List<TmdbMovie> result = movieService.getMovies(2,100);

        Assertions.assertEquals(2, result.size());
        Assertions.assertThrows(GeneralWebserviceException.class, () -> {
            movieService.getMovies(9,100);
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
            public TmdbMovie getTmdbMovieById(Long movieId, boolean includeDetails) {
                return tmdbMovieMap.get(movieId);
            }
            // Override the random ID generator, in order to test the get movies method efficiently
            @Override
            public long getRandomMovieId(double lamda) {
                long result = i;
                i++;
                return result;
            }
        };
        final List<TmdbMovie> result = movieService.getMovies(1, 100);

        Assertions.assertEquals(1, result.size());
        Assertions.assertThrows(GeneralWebserviceException.class, () -> {
            movieService.getMovies(2, 100);
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
            long randomId = movieService.getRandomMovieId(0.2);
            Assertions.assertTrue(randomId >= 1 && randomId <= 5);
        }
    }

    /**
     * Test getMovieById
     */
    @Test
    public void testGetMovieById() {
        when(tmdbClient.getMovie(Mockito.eq("123"), Mockito.anyString())).thenReturn(TmdbMovie.builder().id(1L).posterPath("P").popularity(5.0).build());
        when(tmdbClient.getCast(Mockito.eq("123"), Mockito.anyString())).thenReturn(TmdbCast.builder().cast(Collections.singletonList(CastMember.builder().name("N").build())).build());
        when(tmdbClient.getWatchProvider(Mockito.eq("123"), Mockito.anyString())).thenReturn(TmdbWatchProvider.builder().results(Collections.singletonMap("CH", TmdbWatchProviderCountry.builder().link("L1")
                .buy(Arrays.asList(WatchProvider.builder().displayPriority(3).providerName("P3").logoPath("L3").build(), WatchProvider.builder().displayPriority(1).providerName("P4").logoPath("L4").build()))
                .flatrate(Arrays.asList(WatchProvider.builder().displayPriority(3).providerName("P3").logoPath("L3").build(), WatchProvider.builder().displayPriority(1).providerName("P4").logoPath("L4").build()))
                .rent(Arrays.asList(WatchProvider.builder().displayPriority(3).providerName("P3").logoPath("L3").build(), WatchProvider.builder().displayPriority(1).providerName("P4").logoPath("L4").build())).build())).build());
        TmdbMovie movie = movieService.getTmdbMovieById(123L, true);
        Assertions.assertEquals(1L, movie.getId());
        Assertions.assertEquals("N", movie.getCast().getCast().get(0).getName());
        Assertions.assertEquals("L1", movie.getWatchProviders().getLink());
        Assertions.assertEquals("P3", movie.getWatchProviders().getRent().get(0).getProviderName());
        Assertions.assertEquals("P3", movie.getWatchProviders().getBuy().get(0).getProviderName());
        Assertions.assertEquals("P3", movie.getWatchProviders().getFlatrate().get(0).getProviderName());
    }

    /**
     * Test getMovieById exception
     */
    @Test
    public void testGetMovieByIdException() {
        ReflectionTestUtils.setField(movieService, "tmdbApiKey", null);
        Assertions.assertThrows(GeneralWebserviceException.class, () -> {
            movieService.getTmdbMovieById(1L, true);
        });
    }

    /**
     * Test getMovieById catch feign exception
     */
    @Test
    public void testGetMovieByIdException2() {
        when(tmdbClient.getMovie(Mockito.eq("1"), Mockito.anyString())).thenThrow(exception);
        Assertions.assertNull(movieService.getTmdbMovieById(1L, true));
    }



}
