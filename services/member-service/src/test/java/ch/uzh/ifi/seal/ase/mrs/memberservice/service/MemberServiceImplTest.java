package ch.uzh.ifi.seal.ase.mrs.memberservice.service;

import ch.uzh.ifi.seal.ase.mrs.memberservice.exception.GeneralWebserviceException;
import ch.uzh.ifi.seal.ase.mrs.memberservice.model.*;
import ch.uzh.ifi.seal.ase.mrs.memberservice.model.dto.ActorRatingDto;
import ch.uzh.ifi.seal.ase.mrs.memberservice.model.dto.MovieRatingDto;
import ch.uzh.ifi.seal.ase.mrs.memberservice.repository.ActorRepository;
import ch.uzh.ifi.seal.ase.mrs.memberservice.repository.RatingRepository;
import ch.uzh.ifi.seal.ase.mrs.memberservice.repository.UserRepository;
import ch.uzh.ifi.seal.ase.mrs.memberservice.service.impl.MemberServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * Test MemberServiceImpl
 */
@ExtendWith(MockitoExtension.class)
public class MemberServiceImplTest {

    @InjectMocks
    MemberServiceImpl memberService;

    @Mock
    UserRepository userRepository;

    @Mock
    RatingRepository ratingRepository;

    @Mock
    ActorRepository actorRepository;

    @Mock
    Principal principal;

    /**
     * Test checkRateMovie
     */
    @Test
    public void checkRateMovie() {
        User user = User.builder().id(1L).password("1234").username("admin").tblRatingUserId(3L).build();
        when(userRepository.findByUsername("admin")).thenReturn(Optional.of(user));
        when(principal.getName()).thenReturn("admin");
        MovieRatingDto movieRatingDto = MovieRatingDto.builder().tmdbId(1L).rating(5.0).build();
        memberService.rateMovie(movieRatingDto, principal);
        ArgumentCaptor<MovieRating> argumentCaptor = ArgumentCaptor.forClass(MovieRating.class);
        verify(ratingRepository).save(argumentCaptor.capture());
        MovieRating capturedArgument = argumentCaptor.<User> getValue();

        Assertions.assertEquals(movieRatingDto.getTmdbId(), capturedArgument.getTmdbId());
        Assertions.assertEquals(movieRatingDto.getRating(), capturedArgument.getRating());
    }

    /**
     * Test checkRateMovie exception
     */
    @Test
    public void checkRateMovieException() {
        when(userRepository.findByUsername("admin")).thenReturn(Optional.empty());
        when(principal.getName()).thenReturn("admin");
        MovieRatingDto movieRatingDto = MovieRatingDto.builder().tmdbId(1L).rating(5.0).build();
        Assertions.assertThrows(GeneralWebserviceException.class, () -> {
            memberService.rateMovie(movieRatingDto, principal);
        });
    }

    /**
     * Test checkRateActor
     */
    @Test
    public void checkRateActor() {
        User user = User.builder().id(1L).password("1234").username("admin").tblRatingUserId(3L).build();
        Actor actor = Actor.builder().id(2L).tmdbId(1L).score(10.0).top1Movie(1L).top2Movie(2L).top3Movie(3L).build();
        when(userRepository.findByUsername("admin")).thenReturn(Optional.of(user));
        when(principal.getName()).thenReturn("admin");
        when(actorRepository.findByTmdbId(1L)).thenReturn(Optional.of(actor));
        ActorRatingDto actorRatingDto = ActorRatingDto.builder().tmdbId(1L).rating(5.0).build();


        memberService.rateActor(actorRatingDto, principal);
        ArgumentCaptor<MovieRating> argumentCaptor = ArgumentCaptor.forClass(MovieRating.class);
        verify(ratingRepository, times(3)).save(argumentCaptor.capture());
        List<MovieRating> capturedArguments = argumentCaptor.<User> getAllValues();

        Assertions.assertEquals(actor.getTop1Movie(), capturedArguments.get(0).getTmdbId());
        Assertions.assertEquals(actorRatingDto.getRating(), capturedArguments.get(0).getRating());
        Assertions.assertEquals(actor.getTop2Movie(), capturedArguments.get(1).getTmdbId());
        Assertions.assertEquals(actorRatingDto.getRating(), capturedArguments.get(1).getRating());
        Assertions.assertEquals(actor.getTop3Movie(), capturedArguments.get(2).getTmdbId());
        Assertions.assertEquals(actorRatingDto.getRating(), capturedArguments.get(2).getRating());
    }

    /**
     * Test checkRateActor with nulls 1
     */
    @Test
    public void checkRateActorNulls1() {
        User user = User.builder().id(1L).password("1234").username("admin").tblRatingUserId(3L).build();
        Actor actor = Actor.builder().id(2L).tmdbId(1L).score(10.0).top1Movie(1L).top2Movie(null).top3Movie(null).build();
        when(userRepository.findByUsername("admin")).thenReturn(Optional.of(user));
        when(principal.getName()).thenReturn("admin");
        when(actorRepository.findByTmdbId(1L)).thenReturn(Optional.of(actor));
        ActorRatingDto actorRatingDto = ActorRatingDto.builder().tmdbId(1L).rating(5.0).build();


        memberService.rateActor(actorRatingDto, principal);
        ArgumentCaptor<MovieRating> argumentCaptor = ArgumentCaptor.forClass(MovieRating.class);
        verify(ratingRepository, times(1)).save(argumentCaptor.capture());
        List<MovieRating> capturedArguments = argumentCaptor.<User> getAllValues();

        Assertions.assertEquals(actor.getTop1Movie(), capturedArguments.get(0).getTmdbId());
        Assertions.assertEquals(actorRatingDto.getRating(), capturedArguments.get(0).getRating());
    }

    /**
     * Test checkRateActor with nulls 2
     */
    @Test
    public void checkRateActorNulls2() {
        User user = User.builder().id(1L).password("1234").username("admin").tblRatingUserId(3L).build();
        Actor actor = Actor.builder().id(2L).tmdbId(1L).score(10.0).top1Movie(null).top2Movie(2L).top3Movie(null).build();
        when(userRepository.findByUsername("admin")).thenReturn(Optional.of(user));
        when(principal.getName()).thenReturn("admin");
        when(actorRepository.findByTmdbId(1L)).thenReturn(Optional.of(actor));
        ActorRatingDto actorRatingDto = ActorRatingDto.builder().tmdbId(1L).rating(5.0).build();


        memberService.rateActor(actorRatingDto, principal);
        ArgumentCaptor<MovieRating> argumentCaptor = ArgumentCaptor.forClass(MovieRating.class);
        verify(ratingRepository, times(1)).save(argumentCaptor.capture());
        List<MovieRating> capturedArguments = argumentCaptor.<User> getAllValues();

        Assertions.assertEquals(actor.getTop2Movie(), capturedArguments.get(0).getTmdbId());
        Assertions.assertEquals(actorRatingDto.getRating(), capturedArguments.get(0).getRating());
    }

    /**
     * Test checkRateActor with nulls 3
     */
    @Test
    public void checkRateActorNulls3() {
        User user = User.builder().id(1L).password("1234").username("admin").tblRatingUserId(3L).build();
        Actor actor = Actor.builder().id(2L).tmdbId(1L).score(10.0).top1Movie(null).top2Movie(null).top3Movie(3L).build();
        when(userRepository.findByUsername("admin")).thenReturn(Optional.of(user));
        when(principal.getName()).thenReturn("admin");
        when(actorRepository.findByTmdbId(1L)).thenReturn(Optional.of(actor));
        ActorRatingDto actorRatingDto = ActorRatingDto.builder().tmdbId(1L).rating(5.0).build();


        memberService.rateActor(actorRatingDto, principal);
        ArgumentCaptor<MovieRating> argumentCaptor = ArgumentCaptor.forClass(MovieRating.class);
        verify(ratingRepository, times(1)).save(argumentCaptor.capture());
        List<MovieRating> capturedArguments = argumentCaptor.<User> getAllValues();

        Assertions.assertEquals(actor.getTop3Movie(), capturedArguments.get(0).getTmdbId());
        Assertions.assertEquals(actorRatingDto.getRating(), capturedArguments.get(0).getRating());
    }

    /**
     * Test checkRateActor exception
     */
    @Test
    public void checkRateActorException() {
        when(userRepository.findByUsername("admin")).thenReturn(Optional.empty());
        when(principal.getName()).thenReturn("admin");
        ActorRatingDto actorRatingDto = ActorRatingDto.builder().tmdbId(1L).rating(5.0).build();
        Assertions.assertThrows(GeneralWebserviceException.class, () -> {
            memberService.rateActor(actorRatingDto, principal);
        });
    }



}
