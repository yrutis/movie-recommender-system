package ch.uzh.ifi.seal.ase.mrs.freeservice.model.tmdb;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;

/**
 * Test TmdbWatchProvider
 */
public class TmdbWatchProviderTest {
    /**
     * Test the TmdbWatchProvider Builder
     */
    @Test
    void testActorBuilder() {
        TmdbWatchProvider tmdbWatchProvider = TmdbWatchProvider.builder().results(Collections.singletonMap("CH", TmdbWatchProviderCountry.builder().link("L1")
                .buy(Arrays.asList(WatchProvider.builder().displayPriority(3).providerName("P3").logoPath("L3").build(), WatchProvider.builder().displayPriority(1).providerName("P4").logoPath("L4").build()))
                .flatrate(Arrays.asList(WatchProvider.builder().displayPriority(3).providerName("P3").logoPath("L3").build(), WatchProvider.builder().displayPriority(1).providerName("P4").logoPath("L4").build()))
                .rent(Arrays.asList(WatchProvider.builder().displayPriority(3).providerName("P3").logoPath("L3").build(), WatchProvider.builder().displayPriority(1).providerName("P4").logoPath("L4").build())).build())).build();

        Assertions.assertEquals("L1", tmdbWatchProvider.getResults().get("CH").getLink());
    }
}
