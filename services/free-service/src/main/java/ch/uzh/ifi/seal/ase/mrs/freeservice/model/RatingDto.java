package ch.uzh.ifi.seal.ase.mrs.freeservice.model;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

/**
 * DTO to combine movie and actor ratings
 */
@Getter
@Builder
public class RatingDto {

    /**
     * List of movie ratings
     */
    private List<MovieRating> movieRatings;

    /***
     * List of actor ratings
     */
    private List<ActorRating> actorRatings;
}
