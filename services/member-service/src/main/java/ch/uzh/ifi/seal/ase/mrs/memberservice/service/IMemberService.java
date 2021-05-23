package ch.uzh.ifi.seal.ase.mrs.memberservice.service;

import ch.uzh.ifi.seal.ase.mrs.memberservice.model.dto.ActorRatingDto;
import ch.uzh.ifi.seal.ase.mrs.memberservice.model.dto.MovieRatingDto;

import java.security.Principal;

/**
 * Interface of Member Service
 */
public interface IMemberService {
    /**
     * Save a movie rating of a user
     * @param movieRatingDto rating of the movie
     * @param principal user
     */
    void rateMovie(MovieRatingDto movieRatingDto, Principal principal);

    /**
     * Save a actor rating of a user
     * @param actorRatingDto rating of the actor
     * @param principal user
     */
    void rateActor(ActorRatingDto actorRatingDto, Principal principal);
}
