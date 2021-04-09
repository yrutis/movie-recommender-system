package ch.uzh.ifi.seal.ase.mrs.memberservice.service;

import ch.uzh.ifi.seal.ase.mrs.memberservice.model.ActorRating;
import ch.uzh.ifi.seal.ase.mrs.memberservice.model.MovieRating;

import java.security.Principal;

/**
 * Interface of Member Service
 */
public interface IMemberService {
    void rateMovie(MovieRating movieRating, Principal principal);
    void rateActor(ActorRating actorRating, Principal principal);
}
