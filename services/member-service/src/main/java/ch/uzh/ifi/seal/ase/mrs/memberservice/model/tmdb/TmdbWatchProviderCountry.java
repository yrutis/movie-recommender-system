package ch.uzh.ifi.seal.ase.mrs.memberservice.model.tmdb;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import java.util.List;

/**
 * TmdbWatchProviderCountry Class
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class TmdbWatchProviderCountry {
    /**
     * List of cast members
     */
    private String link;
    private List<WatchProvider> rent;
    private List<WatchProvider> flatrate;
    private List<WatchProvider> buy;
}