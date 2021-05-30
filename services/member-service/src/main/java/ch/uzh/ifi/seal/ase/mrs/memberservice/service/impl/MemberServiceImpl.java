package ch.uzh.ifi.seal.ase.mrs.memberservice.service.impl;

import ch.uzh.ifi.seal.ase.mrs.memberservice.exception.GeneralWebserviceException;
import ch.uzh.ifi.seal.ase.mrs.memberservice.model.*;
import ch.uzh.ifi.seal.ase.mrs.memberservice.model.dto.ActorRatingDto;
import ch.uzh.ifi.seal.ase.mrs.memberservice.model.dto.MovieRatingDto;
import ch.uzh.ifi.seal.ase.mrs.memberservice.repository.ActorRepository;
import ch.uzh.ifi.seal.ase.mrs.memberservice.repository.RatingRepository;
import ch.uzh.ifi.seal.ase.mrs.memberservice.repository.UserRepository;
import ch.uzh.ifi.seal.ase.mrs.memberservice.service.IMemberService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.security.Principal;

/**
 * Service that handles movie and actor ratings and gets recommendations
 */
@Service
@Slf4j
public class MemberServiceImpl implements IMemberService {

    private final UserRepository userRepository;
    private final RatingRepository ratingRepository;
    private final ActorRepository actorRepository;

    @Autowired
    public MemberServiceImpl(UserRepository userRepository, RatingRepository ratingRepository, ActorRepository actorRepository) {
        this.userRepository = userRepository;
        this.ratingRepository = ratingRepository;
        this.actorRepository = actorRepository;
    }

    /**
     * Save a movie rating of a user
     * @param movieRatingDto rating of the movie
     * @param principal user
     */
    @Override
    public void rateMovie(MovieRatingDto movieRatingDto, Principal principal) {
        User user = userRepository.findByUsername(principal.getName()).orElseThrow(() -> GeneralWebserviceException.builder().errorCode("U001").message("Username not found").status(HttpStatus.NOT_FOUND).build());
        MovieRating movieRating = MovieRating.builder().rating(movieRatingDto.getRating()).tmdbId(movieRatingDto.getTmdbId()).userId(user.getTblRatingUserId()).build();
        ratingRepository.save(movieRating);
    }

    /**
     * Save a actor rating of a user
     * @param actorRatingDto rating of the actor
     * @param principal user
     */
    @Override
    public void rateActor(ActorRatingDto actorRatingDto, Principal principal) {
        User user = userRepository.findByUsername(principal.getName()).orElseThrow(() -> GeneralWebserviceException.builder().errorCode("U001").message("Username not found").status(HttpStatus.NOT_FOUND).build());
        Actor actor = actorRepository.findByTmdbId(actorRatingDto.getTmdbId()).orElseThrow(() -> GeneralWebserviceException.builder().errorCode("A001").message("Actor not found").status(HttpStatus.NOT_FOUND).build());
        if(actor.getTop1Movie() != null){
            MovieRating movieRating1 = MovieRating.builder().rating(actorRatingDto.getRating()).tmdbId(actor.getTop1Movie()).userId(user.getTblRatingUserId()).build();
            ratingRepository.save(movieRating1);
        }
        if(actor.getTop2Movie() != null){
            MovieRating movieRating2 = MovieRating.builder().rating(actorRatingDto.getRating()).tmdbId(actor.getTop2Movie()).userId(user.getTblRatingUserId()).build();
            ratingRepository.save(movieRating2);
        }
        if(actor.getTop3Movie() != null){
            MovieRating movieRating3 = MovieRating.builder().rating(actorRatingDto.getRating()).tmdbId(actor.getTop3Movie()).userId(user.getTblRatingUserId()).build();
            ratingRepository.save(movieRating3);
        }
    }
}
