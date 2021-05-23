package ch.uzh.ifi.seal.ase.mrs.memberservice.model.tmdb;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

/**
 * Genre
 */
@AllArgsConstructor
@Getter
@Builder
public class Genre {
    /**
     * Id of the genre
     */
    private long id;

    /**
     * Name of the genre
     */
    private String name;
}
