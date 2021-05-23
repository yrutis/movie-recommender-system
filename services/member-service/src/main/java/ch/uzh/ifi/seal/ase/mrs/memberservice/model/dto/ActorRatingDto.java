package ch.uzh.ifi.seal.ase.mrs.memberservice.model.dto;

import lombok.Builder;
import lombok.Getter;


/**
 * DTO for storing a actor rating
 */
@Getter
@Builder
public class ActorRatingDto {

    /**
     * Id of rated actor
     */
    private final long tmdbId;

    /**
     * Rating of the actor
     */
    private final double rating;
}
