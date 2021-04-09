package ch.uzh.ifi.seal.ase.mrs.freeservice.model.tmdb;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

/**
 * TMDB Actor Class
 */
@AllArgsConstructor
@Getter
@Builder
public class TmdbActor {

    /**
     * Id of the Actor
     */
    private Long id;

    /**
     * Name of the Actor
     */
    private String name;

    /**
     * Path of image of the Actor
     */
    private String profilePath;

    /**
     * Use other value for serialization and deserialization
     * @return Path of image of the Actor
     */
    @JsonProperty("profilePath")
    public String getProfilePath() {
        return this.profilePath;
    }

    /**
     * Use other value for serialization and deserialization
     * @param profilePath Path of image of the Actor
     */
    @JsonProperty("profile_path")
    public void setProfilePath(String profilePath) {
        this.profilePath = profilePath;
    }
}
