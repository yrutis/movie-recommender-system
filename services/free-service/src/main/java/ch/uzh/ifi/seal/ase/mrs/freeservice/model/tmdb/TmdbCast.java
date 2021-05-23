package ch.uzh.ifi.seal.ase.mrs.freeservice.model.tmdb;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import java.util.List;

/**
 * TmdbCast Class
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class TmdbCast {
    /**
     * List of cast members
     */
    private List<CastMember> cast;
}