package ch.uzh.ifi.seal.ase.mrs.freeservice.model;

import lombok.Builder;
import lombok.Getter;


/**
 * DTO for storing a actor rating
 */
@Getter
@Builder
public class ActorRating {

    /**
     * Id of rated actor
     */
    private final long actorId;

    /**
     * Rating of the actor
     */
    private final double rating;
}
