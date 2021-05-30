package ch.uzh.ifi.seal.ase.mrs.memberservice.service;

import ch.uzh.ifi.seal.ase.mrs.memberservice.client.TmdbClient;
import ch.uzh.ifi.seal.ase.mrs.memberservice.exception.GeneralWebserviceException;
import ch.uzh.ifi.seal.ase.mrs.memberservice.model.*;
import ch.uzh.ifi.seal.ase.mrs.memberservice.model.tmdb.*;
import ch.uzh.ifi.seal.ase.mrs.memberservice.repository.RatingRepository;
import ch.uzh.ifi.seal.ase.mrs.memberservice.repository.UserRepository;
import ch.uzh.ifi.seal.ase.mrs.memberservice.service.impl.RecommendationServiceImpl;
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

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

/**
 * Test MemberServiceImpl
 */
@ExtendWith(MockitoExtension.class)
public class RecommendationServiceImplTest {

    @InjectMocks
    RecommendationServiceImpl recommendationService;

    @Mock
    UserRepository userRepository;

    @Mock
    RatingRepository ratingRepository;

    @Mock
    TmdbClient tmdbClient;

    @Mock
    Principal principal;

    @Mock
    FeignException.FeignClientException exception;

    @BeforeEach
    public void setUp() {
        ReflectionTestUtils.setField(recommendationService, "tmdbApiKey", "value");
        ReflectionTestUtils.setField(recommendationService, "tmdbWatchProviderLocale", "CH");
    }

    /**
     * Test getRecommendations
     */
    @Test
    public void testGetRecommendations() {
        User user = User.builder().id(1L).password("1234").username("admin").tblRatingUserId(3L).build();
        when(userRepository.findByUsername("admin")).thenReturn(Optional.of(user));
        when(principal.getName()).thenReturn("admin");
        MovieRating movieRating1 = MovieRating.builder().id(1L).rating(4.0).tmdbId(1L).userId(3L).build();
        when(ratingRepository.findAllByUserId(user.getTblRatingUserId())).thenReturn(Collections.singletonList(movieRating1));
        when(tmdbClient.getMovie(eq("1"), Mockito.anyString())).thenReturn(TmdbMovie.builder().id(1L).title("1").posterPath("path").popularity(12.0).build());
        when(tmdbClient.getCast(Mockito.eq("1"), Mockito.anyString())).thenReturn(TmdbCast.builder().cast(Collections.singletonList(CastMember.builder().name("N").build())).build());
        when(tmdbClient.getWatchProvider(Mockito.eq("1"), Mockito.anyString())).thenReturn(TmdbWatchProvider.builder().results(Collections.singletonMap("CH", TmdbWatchProviderCountry.builder().link("L1")
                .buy(Arrays.asList(WatchProvider.builder().displayPriority(3).providerName("P3").logoPath("L3").build(), WatchProvider.builder().displayPriority(1).providerName("P4").logoPath("L4").build()))
                .flatrate(Arrays.asList(WatchProvider.builder().displayPriority(3).providerName("P3").logoPath("L3").build(), WatchProvider.builder().displayPriority(1).providerName("P4").logoPath("L4").build()))
                .rent(Arrays.asList(WatchProvider.builder().displayPriority(3).providerName("P3").logoPath("L3").build(), WatchProvider.builder().displayPriority(1).providerName("P4").logoPath("L4").build())).build())).build());

        List<TmdbMovie> result = recommendationService.getRecommendations(principal);
        Assertions.assertEquals(12.0, result.get(0).getPopularity());
        Assertions.assertEquals(1L, result.get(0).getId());
        Assertions.assertEquals(1L, result.get(0).getId());
        Assertions.assertEquals("N", result.get(0).getCast().getCast().get(0).getName());
        Assertions.assertEquals("L1", result.get(0).getWatchProviders().getLink());
        Assertions.assertEquals("P3", result.get(0).getWatchProviders().getRent().get(0).getProviderName());
        Assertions.assertEquals("P3", result.get(0).getWatchProviders().getBuy().get(0).getProviderName());
        Assertions.assertEquals("P3", result.get(0).getWatchProviders().getFlatrate().get(0).getProviderName());
    }

    /**
     * Test getLastTrained
     */
    @Test
    public void testGetLastTrained() {
        LocalDateTime localDateTime = LocalDateTime.now();
        User user = User.builder().id(1L).password("1234").username("admin").tblRatingUserId(3L).lastTrainedOn(localDateTime).build();
        when(userRepository.findByUsername("admin")).thenReturn(Optional.of(user));
        when(principal.getName()).thenReturn("admin");
        Assertions.assertEquals(localDateTime, recommendationService.getLastTrained(principal));

        when(userRepository.findByUsername("admin")).thenReturn(Optional.empty());
        Assertions.assertThrows(GeneralWebserviceException.class, () -> {
            recommendationService.getLastTrained(principal);
        });
    }

    /**
     * Test getRecommendations Exception
     */
    @Test
    public void testGetRecommendationsException() {
        when(userRepository.findByUsername("admin")).thenReturn(Optional.empty());
        when(principal.getName()).thenReturn("admin");
        Assertions.assertThrows(GeneralWebserviceException.class, () -> {
            recommendationService.getRecommendations(principal);
        });
    }

    /**
     * Test getRecommendations no API
     */
    @Test
    public void testGetRecommendationsNoAPI() {
        ReflectionTestUtils.setField(recommendationService, "tmdbApiKey", null);
        User user = User.builder().id(1L).password("1234").username("admin").tblRatingUserId(3L).build();
        when(userRepository.findByUsername("admin")).thenReturn(Optional.of(user));
        when(principal.getName()).thenReturn("admin");
        MovieRating movieRating1 = MovieRating.builder().id(1L).rating(4.0).tmdbId(1L).userId(3L).build();
        MovieRating movieRating2 = MovieRating.builder().id(2L).rating(3.0).tmdbId(2L).userId(3L).build();
        when(ratingRepository.findAllByUserId(user.getTblRatingUserId())).thenReturn(Arrays.asList(movieRating1,movieRating2));
        Assertions.assertThrows(GeneralWebserviceException.class, () -> {
            recommendationService.getRecommendations(principal);
        });
    }

    /**
     * Test getRecommendations Feign Problem
     */
    @Test
    public void testGetRecommendationsFeign() {
        User user = User.builder().id(1L).password("1234").username("admin").tblRatingUserId(3L).build();
        when(userRepository.findByUsername("admin")).thenReturn(Optional.of(user));
        when(principal.getName()).thenReturn("admin");
        MovieRating movieRating1 = MovieRating.builder().id(1L).rating(4.0).tmdbId(1L).userId(3L).build();
        MovieRating movieRating2 = MovieRating.builder().id(2L).rating(3.0).tmdbId(2L).userId(3L).build();
        when(ratingRepository.findAllByUserId(user.getTblRatingUserId())).thenReturn(Arrays.asList(movieRating1,movieRating2));
        when(tmdbClient.getMovie(eq("1"), Mockito.anyString())).thenThrow(exception);
        when(tmdbClient.getMovie(eq("2"), Mockito.anyString())).thenThrow(exception);
        List<TmdbMovie> result = recommendationService.getRecommendations(principal);
        Assertions.assertEquals(0, result.size());

    }


}
