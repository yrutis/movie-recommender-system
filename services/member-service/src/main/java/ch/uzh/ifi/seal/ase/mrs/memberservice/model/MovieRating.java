package ch.uzh.ifi.seal.ase.mrs.memberservice.model;

import lombok.*;

import javax.persistence.*;

/**
 * Class for storing movie ratings
 */
@Getter
@Setter
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tbl_rating")
public class MovieRating {
    /**
     * Id of the Rating
     * The Sequence generator with an offset of 5000000 is used since the IDENTITY Strategy does not work properly
     * with postgres when inserting values with a sql script on startup
     */
    @Id
    @SequenceGenerator(name = "SEQ_G",
            initialValue = 5000000,
            allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_G" )
    private Long id;

    /**
     * Tmdb Movie Id
     */
    private Long tmdbId;

    /**
     * User Id
     */
    private Long userId;

    /**
     * Rating
     */
    private Double rating;
}
