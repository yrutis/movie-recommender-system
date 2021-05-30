package ch.uzh.ifi.seal.ase.mrs.freeservice.model.tmdb;

import ch.uzh.ifi.seal.ase.mrs.freeservice.model.tmdb.TmdbMovie;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.Map;

/**
 * TmdbMovieTest
 */
class TmdbMovieTest {

    /**
     * Test the TmdbMovieTest Builder
     */
    @Test
    void testMovieBuilder() {
        TmdbMovie tmdbMovie = TmdbMovie.builder()
                .id(1L)
                .title("Title")
                .budget(10.0)
                .imdbId("10")
                .originalLanguage("en")
                .originalTitle("origTitle")
                .overview("overview")
                .posterPath("Path")
                .tagline("Tagline")
                .popularity(10.0)
                .releaseDate(LocalDate.now())
                .revenue(100.0)
                .runtime(105)
                .voteAverage(19.7)
                .voteCount(100)
                .genres(Arrays.asList(Genre.builder().id(1).name("G1").build(), Genre.builder().id(2).name("G2").build()))
                .productionCompanies(Arrays.asList(ProductionCompany.builder().name("P1").originCountry("US").build(), ProductionCompany.builder().name("P2").originCountry("US").build()))
                .productionCountries(Arrays.asList(ProductionCountry.builder().countryCode("us").name("C1").build(), ProductionCountry.builder().countryCode("us").name("C2").build()))
                .spokenLanguages(Arrays.asList(SpokenLanguage.builder().englishName("English").languageCode("en").name("English").build(), SpokenLanguage.builder().englishName("German").languageCode("de").name("Deutsch").build()))
                .cast(TmdbCast.builder().cast(Arrays.asList(CastMember.builder().character("C1").name("N1").order(1).build(), CastMember.builder().character("C2").name("N2").order(2).build())).build())
                .watchProviders(TmdbWatchProviderCountry.builder().link("L1")
                        .buy(Arrays.asList(WatchProvider.builder().displayPriority(1).providerName("P1").logoPath("L1").build(), WatchProvider.builder().displayPriority(1).providerName("P2").logoPath("L2").build()))
                        .flatrate(Arrays.asList(WatchProvider.builder().displayPriority(1).providerName("P1").logoPath("L1").build(), WatchProvider.builder().displayPriority(1).providerName("P2").logoPath("L2").build()))
                        .rent(Arrays.asList(WatchProvider.builder().displayPriority(1).providerName("P1").logoPath("L1").build(), WatchProvider.builder().displayPriority(1).providerName("P2").logoPath("L2").build())).build()).build();
        Assertions.assertEquals(1L, tmdbMovie.getId());
        Assertions.assertEquals("Title", tmdbMovie.getTitle());
        Assertions.assertEquals(10.0, tmdbMovie.getBudget());
        Assertions.assertEquals("10", tmdbMovie.getImdbId());
        Assertions.assertEquals("en", tmdbMovie.getOriginalLanguage());
        Assertions.assertEquals("origTitle", tmdbMovie.getOriginalTitle());
        Assertions.assertEquals("overview", tmdbMovie.getOverview());
        Assertions.assertEquals("Path", tmdbMovie.getPosterPath());
        Assertions.assertEquals("Tagline", tmdbMovie.getTagline());
        Assertions.assertEquals(10.0, tmdbMovie.getPopularity());
        Assertions.assertEquals(LocalDate.now(), tmdbMovie.getReleaseDate());
        Assertions.assertEquals(100.0, tmdbMovie.getRevenue());
        Assertions.assertEquals(105, tmdbMovie.getRuntime());
        Assertions.assertEquals(19.7, tmdbMovie.getVoteAverage());
        Assertions.assertEquals(100, tmdbMovie.getVoteCount());

        Assertions.assertEquals(2, tmdbMovie.getGenres().size());
        Assertions.assertEquals("G1", tmdbMovie.getGenres().get(0).getName());
        Assertions.assertEquals(1, tmdbMovie.getGenres().get(0).getId());

        Assertions.assertEquals(2, tmdbMovie.getProductionCompanies().size());
        Assertions.assertEquals("P1", tmdbMovie.getProductionCompanies().get(0).getName());
        Assertions.assertEquals("US", tmdbMovie.getProductionCompanies().get(0).getOriginCountry());

        Assertions.assertEquals(2, tmdbMovie.getProductionCountries().size());
        Assertions.assertEquals("C1", tmdbMovie.getProductionCountries().get(0).getName());
        Assertions.assertEquals("us", tmdbMovie.getProductionCountries().get(0).getCountryCode());

        Assertions.assertEquals(2, tmdbMovie.getSpokenLanguages().size());
        Assertions.assertEquals("English", tmdbMovie.getSpokenLanguages().get(0).getName());
        Assertions.assertEquals("English", tmdbMovie.getSpokenLanguages().get(0).getEnglishName());
        Assertions.assertEquals("en", tmdbMovie.getSpokenLanguages().get(0).getLanguageCode());

        Assertions.assertEquals("C1", tmdbMovie.getCast().getCast().get(0).getCharacter());
        Assertions.assertEquals("N1", tmdbMovie.getCast().getCast().get(0).getName());
        Assertions.assertEquals(1, tmdbMovie.getCast().getCast().get(0).getOrder());

        Assertions.assertEquals("P1", tmdbMovie.getWatchProviders().getBuy().get(0).getProviderName());
        Assertions.assertEquals("L1", tmdbMovie.getWatchProviders().getBuy().get(0).getLogoPath());
        Assertions.assertEquals(1, tmdbMovie.getWatchProviders().getBuy().get(0).getDisplayPriority());
        Assertions.assertEquals("P1", tmdbMovie.getWatchProviders().getFlatrate().get(0).getProviderName());
        Assertions.assertEquals("L1", tmdbMovie.getWatchProviders().getFlatrate().get(0).getLogoPath());
        Assertions.assertEquals(1, tmdbMovie.getWatchProviders().getFlatrate().get(0).getDisplayPriority());
        Assertions.assertEquals("P1", tmdbMovie.getWatchProviders().getRent().get(0).getProviderName());
        Assertions.assertEquals("L1", tmdbMovie.getWatchProviders().getRent().get(0).getLogoPath());
        Assertions.assertEquals(1, tmdbMovie.getWatchProviders().getRent().get(0).getDisplayPriority());

        tmdbMovie.setImdbId("1");
        tmdbMovie.setOriginalLanguage("2");
        tmdbMovie.setOriginalTitle("3");
        SpokenLanguage spokenLanguage = SpokenLanguage.builder().englishName("x").languageCode("x").name("English").build();
        spokenLanguage.setEnglishName("English");
        spokenLanguage.setLanguageCode("en");
        tmdbMovie.setSpokenLanguages(Arrays.asList(spokenLanguage, SpokenLanguage.builder().englishName("German").languageCode("de").name("Deutsch").build()));
        ProductionCompany productionCompany = ProductionCompany.builder().name("P1").originCountry("x").build();
        productionCompany.setOriginCountry("US");
        tmdbMovie.setProductionCompanies(Arrays.asList(productionCompany, ProductionCompany.builder().name("P2").originCountry("US").build()));
        ProductionCountry productionCountry = ProductionCountry.builder().countryCode("x").name("C1").build();
        productionCountry.setCountryCode("us");
        tmdbMovie.setProductionCountries(Arrays.asList(productionCountry, ProductionCountry.builder().countryCode("us").name("C2").build()));
        tmdbMovie.setPosterPath("4");
        tmdbMovie.setVoteAverage(4.0);
        tmdbMovie.setVoteCount(4);
        LocalDate localDate = LocalDate.now();
        tmdbMovie.setReleaseDate(localDate);
        tmdbMovie.setCast(TmdbCast.builder().cast(Arrays.asList(CastMember.builder().character("C3").name("N3").order(3).build(), CastMember.builder().character("C4").name("N4").order(4).build())).build());
        tmdbMovie.setWatchProviders(TmdbWatchProviderCountry.builder().link("L1")
                        .buy(Arrays.asList(WatchProvider.builder().displayPriority(3).providerName("P3").logoPath("L3").build(), WatchProvider.builder().displayPriority(1).providerName("P4").logoPath("L4").build()))
                        .flatrate(Arrays.asList(WatchProvider.builder().displayPriority(3).providerName("P3").logoPath("L3").build(), WatchProvider.builder().displayPriority(1).providerName("P4").logoPath("L4").build()))
                        .rent(Arrays.asList(WatchProvider.builder().displayPriority(3).providerName("P3").logoPath("L3").build(), WatchProvider.builder().displayPriority(1).providerName("P4").logoPath("L4").build())).build());

        Assertions.assertEquals("1", tmdbMovie.getImdbId());
        Assertions.assertEquals("2", tmdbMovie.getOriginalLanguage());
        Assertions.assertEquals("3", tmdbMovie.getOriginalTitle());
        Assertions.assertEquals("4", tmdbMovie.getPosterPath());
        Assertions.assertEquals(4.0, tmdbMovie.getVoteAverage());
        Assertions.assertEquals(4, tmdbMovie.getVoteCount());

        Assertions.assertEquals(2, tmdbMovie.getSpokenLanguages().size());
        Assertions.assertEquals("English", tmdbMovie.getSpokenLanguages().get(0).getName());
        Assertions.assertEquals("English", tmdbMovie.getSpokenLanguages().get(0).getEnglishName());
        Assertions.assertEquals("en", tmdbMovie.getSpokenLanguages().get(0).getLanguageCode());

        Assertions.assertEquals(2, tmdbMovie.getProductionCompanies().size());
        Assertions.assertEquals("P1", tmdbMovie.getProductionCompanies().get(0).getName());
        Assertions.assertEquals("US", tmdbMovie.getProductionCompanies().get(0).getOriginCountry());

        Assertions.assertEquals(2, tmdbMovie.getProductionCountries().size());
        Assertions.assertEquals("C1", tmdbMovie.getProductionCountries().get(0).getName());
        Assertions.assertEquals("us", tmdbMovie.getProductionCountries().get(0).getCountryCode());

        Assertions.assertEquals("C3", tmdbMovie.getCast().getCast().get(0).getCharacter());
        Assertions.assertEquals("N3", tmdbMovie.getCast().getCast().get(0).getName());
        Assertions.assertEquals(3, tmdbMovie.getCast().getCast().get(0).getOrder());

        Assertions.assertEquals("P3", tmdbMovie.getWatchProviders().getBuy().get(0).getProviderName());
        Assertions.assertEquals("L3", tmdbMovie.getWatchProviders().getBuy().get(0).getLogoPath());
        Assertions.assertEquals(3, tmdbMovie.getWatchProviders().getBuy().get(0).getDisplayPriority());
        Assertions.assertEquals("P3", tmdbMovie.getWatchProviders().getFlatrate().get(0).getProviderName());
        Assertions.assertEquals("L3", tmdbMovie.getWatchProviders().getFlatrate().get(0).getLogoPath());
        Assertions.assertEquals(3, tmdbMovie.getWatchProviders().getFlatrate().get(0).getDisplayPriority());
        Assertions.assertEquals("P3", tmdbMovie.getWatchProviders().getRent().get(0).getProviderName());
        Assertions.assertEquals("L3", tmdbMovie.getWatchProviders().getRent().get(0).getLogoPath());
        Assertions.assertEquals(3, tmdbMovie.getWatchProviders().getRent().get(0).getDisplayPriority());
    }


}
