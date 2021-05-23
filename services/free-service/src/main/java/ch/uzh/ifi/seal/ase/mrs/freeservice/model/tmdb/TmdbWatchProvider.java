package ch.uzh.ifi.seal.ase.mrs.freeservice.model.tmdb;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import java.util.List;
import java.util.Map;

/**
 * TmdbWatchProvider Class
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class TmdbWatchProvider {
    /**
     * List of cast members
     */
    private Map<String, TmdbWatchProviderCountry> results;
}