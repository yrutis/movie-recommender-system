package ch.uzh.ifi.seal.ase.mrs.freeservice.model.tmdb;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

/**
 * CastMember Class
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class CastMember {
    /**
     * Name of the Actor
     */
    private String name;

    /**
     * Name of the character
     */
    private String character;

    /**
     * Importance of the cast member in the movie
     */
    private Integer order;
}