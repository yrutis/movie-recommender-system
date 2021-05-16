package ch.uzh.ifi.seal.ase.mrs.memberservice.model.tmdb;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

/**
 * Spoken Language
 */
@AllArgsConstructor
@Getter
@Builder
public class SpokenLanguage {

    /**
     * English Name
     */
    private String englishName;
    /**
     * Use other value for serialization and deserialization
     * @return English Name
     */
    @JsonProperty("englishName")
    public String getEnglishName() {
        return this.englishName;
    }

    /**
     * Use other value for serialization and deserialization
     * @param englishName  English Name
     */
    @JsonProperty("english_name")
    public void setEnglishName(String englishName) {
        this.englishName = englishName;
    }

    /**
     * iso_639_1 Language Code
     */
    private String languageCode;

    /**
     * Use other value for serialization and deserialization
     * @return  Language Code
     */
    @JsonProperty("languageCode")
    public String getLanguageCode() {
        return this.languageCode;
    }

    /**
     * Use other value for serialization and deserialization
     * @param languageCode  Language Code
     */
    @JsonProperty("iso_639_1")
    public void setLanguageCode(String languageCode) {
        this.languageCode = languageCode;
    }

    /**
     * Language Original Name
     */
    private String name;
}
