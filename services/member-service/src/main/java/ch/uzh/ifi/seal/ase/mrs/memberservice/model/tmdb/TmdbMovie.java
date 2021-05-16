package ch.uzh.ifi.seal.ase.mrs.memberservice.model.tmdb;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.util.List;


/**
 * TMDB Movie Class
 */
@AllArgsConstructor
@Getter
@Builder
public class TmdbMovie {
    /**
     * Id of the Movie
     */
    private Long id;

    /**
     * IMDB Id
     */
    private String imdbId;

    /**
     * Use other value for serialization and deserialization
     * @return IMDB Id
     */
    @JsonProperty("imdbId")
    public String getImdbId() {
        return this.imdbId;
    }

    /**
     * Use other value for serialization and deserialization
     * @param imdbId IMDB Id
     */
    @JsonProperty("imdb_id")
    public void setImdbId(String imdbId) {
        this.imdbId = imdbId;
    }

    /**
     * Original language
     */
    private String originalLanguage;

    /**
     * Use other value for serialization and deserialization
     * @return Original language
     */
    @JsonProperty("originalLanguage")
    public String getOriginalLanguage() {
        return this.originalLanguage;
    }

    /**
     * Use other value for serialization and deserialization
     * @param originalLanguage Original language
     */
    @JsonProperty("original_language")
    public void setOriginalLanguage(String originalLanguage) {
        this.originalLanguage = originalLanguage;
    }

    /**
     * Original title
     */
    private String originalTitle;

    /**
     * Use other value for serialization and deserialization
     * @return title
     */
    @JsonProperty("originalTitle")
    public String getOriginalTitle() {
        return this.originalTitle;
    }

    /**
     * Use other value for serialization and deserialization
     * @param originalTitle title
     */
    @JsonProperty("original_title")
    public void setOriginalTitle(String originalTitle) {
        this.originalTitle = originalTitle;
    }

    /**
     * Other languages
     */
    private List<SpokenLanguage> spokenLanguages;

    /**
     * Use other value for serialization and deserialization
     * @return Spoken Languages
     */
    @JsonProperty("spokenLanguages")
    public List<SpokenLanguage>  getSpokenLanguages() {
        return this.spokenLanguages;
    }

    /**
     * Use other value for serialization and deserialization
     * @param spokenLanguages Spoken Languages
     */
    @JsonProperty("spoken_languages")
    public void setSpokenLanguages(List<SpokenLanguage>  spokenLanguages) {
        this.spokenLanguages = spokenLanguages;
    }

    /**
     * Title in english
     */
    private String title;

    /**
     * Tagline in english
     */
    private String tagline;

    /**
     * Description of content
     */
    private String overview;

    /**
     * Path to image on tmdb
     */
    private String posterPath;

    /**
     * Use other value for serialization and deserialization
     * @return Poster Path
     */
    @JsonProperty("posterPath")
    public String getPosterPath() {
        return this.posterPath;
    }

    /**
     * Use other value for serialization and deserialization
     * @param posterPath Poster Path
     */
    @JsonProperty("poster_path")
    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    /**
     * Genres of the movies
     */
    private List<Genre> genres;

    /**
     * Production companies
     */
    private List<ProductionCompany> productionCompanies;

    /**
     * Use other value for serialization and deserialization
     * @return Production companies
     */
    @JsonProperty("productionCompanies")
    public List<ProductionCompany>  getProductionCompanies() {
        return this.productionCompanies;
    }

    /**
     * Use other value for serialization and deserialization
     * @param productionCompanies Production companies
     */
    @JsonProperty("production_companies")
    public void setProductionCompanies(List<ProductionCompany>  productionCompanies) {
        this.productionCompanies = productionCompanies;
    }

    /**
     * Production countries
     */
    private List<ProductionCountry> productionCountries;

    /**
     * Use other value for serialization and deserialization
     * @return Production countries
     */
    @JsonProperty("productionCountries")
    public List<ProductionCountry>  getProductionCountries() {
        return this.productionCountries;
    }

    /**
     * Use other value for serialization and deserialization
     * @param productionCountries Production countries
     */
    @JsonProperty("production_countries")
    public void setProductionCountries(List<ProductionCountry>  productionCountries) {
        this.productionCountries = productionCountries;
    }

    /**
     * Popularity
     */
    private Double popularity;

    /**
     * Runtime of the movie in cinemas
     */
    private Integer runtime;

    /**
     * Revenue of the movie
     */
    private Double revenue;

    /**
     * Budget of the movie
     */
    private Double budget;

    /**
     * Average vote of users
     */
    private Double voteAverage;

    /**
     * Use other value for serialization and deserialization
     * @return Average vote of users
     */
    @JsonProperty("voteAverage")
    public Double getVoteAverage() {
        return this.voteAverage;
    }

    /**
     * Use other value for serialization and deserialization
     * @param voteAverage Average vote of users
     */
    @JsonProperty("vote_average")
    public void setVoteAverage(Double voteAverage) {
        this.voteAverage = voteAverage;
    }

    /**
     * Number of votes
     */
    private Integer voteCount;

    /**
     * Use other value for serialization and deserialization
     * @return Number of votes
     */
    @JsonProperty("voteCount")
    public Integer getVoteCount() {
        return this.voteCount;
    }

    /**
     * Use other value for serialization and deserialization
     * @param voteCount  Number of votes
     */
    @JsonProperty("vote_count")
    public void setVoteCount(Integer voteCount) {
        this.voteCount = voteCount;
    }

    /**
     * Release Date
     */
    private LocalDate releaseDate;

    /**
     * Use other value for serialization and deserialization
     * @return Release Date
     */
    @JsonProperty("releaseDate")
    public LocalDate getReleaseDate() {
        return this.releaseDate;
    }

    /**
     * Use other value for serialization and deserialization
     * @param releaseDate  Release Date
     */
    @JsonProperty("release_date")
    public void setReleaseDate(LocalDate releaseDate) {
        this.releaseDate = releaseDate;
    }
}
