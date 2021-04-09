package ch.uzh.ifi.seal.ase.mrs.freeservice.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * Class for storing actors
 */
@Getter
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tbl_actor")
public class Actor {
    /**
     * Id of the Actor
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * TMDB ID of the Actor
     */
    private Long tmdbId;

    /**
     * TMDB Movie ID of the best movie of the Actor
     */
    private Long top1Movie;

    /**
     * TMDB Movie ID of the 2nd best movie of the Actor
     */
    private Long top2Movie;

    /**
     * TMDB Movie ID of the 3rd best movie of the Actor
     */
    private Long top3Movie;

    /**
     * Score of Actor measured by top 3 movies
     */
    private Double score;

}
