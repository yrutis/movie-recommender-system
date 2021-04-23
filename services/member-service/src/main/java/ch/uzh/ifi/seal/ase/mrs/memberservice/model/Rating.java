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
public class Rating {
    /**
     * Id of the Rating
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
