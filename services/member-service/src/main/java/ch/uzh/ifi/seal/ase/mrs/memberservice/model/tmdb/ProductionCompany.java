package ch.uzh.ifi.seal.ase.mrs.memberservice.model.tmdb;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

/**
 * Production Company
 */
@AllArgsConstructor
@Getter
@Builder
public class ProductionCompany {
    /**
     * Origin Country
     */
    private String originCountry;

    /**
     * Use other value for serialization and deserialization
     * @return Origin Country
     */
    @JsonProperty("originCountry")
    public String getOriginCountry() {
        return this.originCountry;
    }

    /**
     * Use other value for serialization and deserialization
     * @param originCountry Origin Country
     */
    @JsonProperty("origin_country")
    public void setOriginCountry(String originCountry) {
        this.originCountry = originCountry;
    }

    /**
     * Company Name
     */
    private String name;
}
