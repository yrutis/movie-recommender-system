package ch.uzh.ifi.seal.ase.mrs.freeservice.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * Test Movie Class
 */
public class MovieTest {
    /**
     * Test the Movie Builder
     */
    @Test
    void testMovieBuilder() {
        Movie movie = Movie.builder()
                .id(1l)
                .tmdbId(1L)
                .imdbId("1")
                .voteCount(1)
                .voteAverage(5.5)
                .popularity(10.0)
                .build();
        Assertions.assertEquals(1l, movie.getId());
        Assertions.assertEquals(1L, movie.getTmdbId());
        Assertions.assertEquals("1", movie.getImdbId());
        Assertions.assertEquals(1, movie.getVoteCount());
        Assertions.assertEquals(5.5, movie.getVoteAverage());
        Assertions.assertEquals(10.0, movie.getPopularity());
    }

    /**
     * Test No Args
     */
    void testMovieNoArgs() {
        Movie movie = new Movie();
        Assertions.assertNotNull(movie);
    }

//    /**
//     * Test the Movie Builder
//     */
//    @Test
//    void testMovieBuilder() {
//        Movie movie = Movie.builder()
//                .id(1L)
//                .originalLanguage("en")
//                .originalTitle("originalTitle")
//                .budget(100.0)
//                .revenue(105.0)
//                .genres("Genres")
//                .imdbId("imdb")
//                .tmdbId(112L)
//                .overview("Overview")
//                .popularity(20.0)
//                .posterPath("Path")
//                .productionCompanies("Companies")
//                .productionCountries("Countries")
//                .runtime(101)
//                .spokenLanguages("languages")
//                .tagline("tagline")
//                .title("title")
//                .voteAverage(8.2)
//                .voteCount(102).build();
//        Assertions.assertEquals(1L, movie.getId());
//        Assertions.assertEquals("en", movie.getOriginalLanguage());
//        Assertions.assertEquals("originalTitle", movie.getOriginalTitle());
//        Assertions.assertEquals(100.0, movie.getBudget());
//        Assertions.assertEquals(105.0, movie.getRevenue());
//        Assertions.assertEquals("Genres", movie.getGenres());
//        Assertions.assertEquals("imdb", movie.getImdbId());
//        Assertions.assertEquals(112L, movie.getTmdbId());
//        Assertions.assertEquals("Overview", movie.getOverview());
//        Assertions.assertEquals(20.0, movie.getPopularity());
//        Assertions.assertEquals("Path", movie.getPosterPath());
//        Assertions.assertEquals("Companies", movie.getProductionCompanies());
//        Assertions.assertEquals("Countries", movie.getProductionCountries());
//        Assertions.assertEquals(101, movie.getRuntime());
//        Assertions.assertEquals("languages", movie.getSpokenLanguages());
//        Assertions.assertEquals("tagline", movie.getTagline());
//        Assertions.assertEquals("title", movie.getTitle());
//        Assertions.assertEquals(8.2, movie.getVoteAverage());
//        Assertions.assertEquals(102, movie.getVoteCount());
//    }
//
//
//    /**
//     * Test the Movie No Args Constructor
//     */
//    @Test
//    void testMovieNoArgs() {
//        Movie movie = new Movie();
//        Assertions.assertNull(movie.getId());
//        Assertions.assertNull(movie.getOriginalLanguage());
//        Assertions.assertNull(movie.getOriginalTitle());
//        Assertions.assertNull(movie.getBudget());
//        Assertions.assertNull(movie.getRevenue());
//        Assertions.assertNull(movie.getGenres());
//        Assertions.assertNull(movie.getImdbId());
//        Assertions.assertNull(movie.getTmdbId());
//        Assertions.assertNull(movie.getOverview());
//        Assertions.assertNull(movie.getPopularity());
//        Assertions.assertNull(movie.getPosterPath());
//        Assertions.assertNull(movie.getProductionCompanies());
//        Assertions.assertNull(movie.getProductionCountries());
//        Assertions.assertNull(movie.getRuntime());
//        Assertions.assertNull(movie.getSpokenLanguages());
//        Assertions.assertNull(movie.getTagline());
//        Assertions.assertNull(movie.getTitle());
//        Assertions.assertNull(movie.getVoteAverage());
//        Assertions.assertNull(movie.getVoteCount());
//
//        movie.setId(1L);
//        movie.setOriginalLanguage("en");
//        movie.setOriginalLanguage("originalTitle");
//        movie.setBudget(100.0);
//        movie.setRevenue(105.0);
//        movie.setGenres("Genres");
//        movie.setImdbId("imdb");
//        movie.setTmdbId(112L);
//        movie.setOverview("Overview");
//        movie.setPopularity(20.0);
//        movie.setPosterPath("Path");
//        movie.setProductionCompanies("Companies");
//        movie.setProductionCountries("Countries");
//        movie.setRuntime(101);
//        movie.setSpokenLanguages("languages");
//        movie.setTagline("tagline");
//        movie.setTitle("title");
//        movie.setVoteAverage(8.2);
//        movie.setVoteCount(102);
//
//        Assertions.assertEquals(1L, movie.getId());
//        Assertions.assertEquals("en", movie.getOriginalLanguage());
//        Assertions.assertEquals("originalTitle", movie.getOriginalTitle());
//        Assertions.assertEquals(100.0, movie.getBudget());
//        Assertions.assertEquals(105.0, movie.getRevenue());
//        Assertions.assertEquals("Genres", movie.getGenres());
//        Assertions.assertEquals("imdb", movie.getImdbId());
//        Assertions.assertEquals(112L, movie.getTmdbId());
//        Assertions.assertEquals("Overview", movie.getOverview());
//        Assertions.assertEquals(20.0, movie.getPopularity());
//        Assertions.assertEquals("Path", movie.getPosterPath());
//        Assertions.assertEquals("Companies", movie.getProductionCompanies());
//        Assertions.assertEquals("Countries", movie.getProductionCountries());
//        Assertions.assertEquals(101, movie.getRuntime());
//        Assertions.assertEquals("languages", movie.getSpokenLanguages());
//        Assertions.assertEquals("tagline", movie.getTagline());
//        Assertions.assertEquals("title", movie.getTitle());
//        Assertions.assertEquals(8.2, movie.getVoteAverage());
//        Assertions.assertEquals(102, movie.getVoteCount());
//    }

}
