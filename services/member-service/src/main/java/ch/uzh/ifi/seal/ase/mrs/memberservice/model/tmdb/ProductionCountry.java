package ch.uzh.ifi.seal.ase.mrs.memberservice.model.tmdb;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

/**
 * Production Country
 */
@AllArgsConstructor
@Getter
@Builder
public class ProductionCountry {

    /**
     * iso_3166_1 Country Code
     */
    private String countryCode;

    /**
     * Use other value for serialization and deserialization
     * @return  Country Code
     */
    @JsonProperty("countryCode")
    public String getCountryCode() {
        return this.countryCode;
    }

    /**
     * Use other value for serialization and deserialization
     * @param countryCode  Country Code
     */
    @JsonProperty("iso_3166_1")
    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    /**
     * Country name
     */
    private String name;
}
