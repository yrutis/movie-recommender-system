package ch.uzh.ifi.seal.ase.mrs.freeservice.model.tmdb;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

/**
 * WatchProvider Class
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class WatchProvider {
    @JsonAlias("provider_name")
    private String providerName;
    @JsonAlias("logo_path")
    private String logoPath;
    @JsonAlias("display_priority")
    private Integer displayPriority;
}
