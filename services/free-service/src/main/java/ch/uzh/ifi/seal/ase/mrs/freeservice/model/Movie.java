package ch.uzh.ifi.seal.ase.mrs.freeservice.model;

import lombok.*;

import javax.persistence.*;

/**
 * Class for storing movies
 */
@Getter
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tbl_movie")
public class Movie {
    /**
     * Id of the Movie
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * The movies DB Id
     */
    private Long tmdbId;

    /**
     * IMDB Id
     */
    private String imdbId;

    /**
     * Score of the movie
     */
    private Double score;

}
