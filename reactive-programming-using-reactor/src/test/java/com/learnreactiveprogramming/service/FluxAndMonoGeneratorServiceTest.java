package com.learnreactiveprogramming.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import reactor.test.StepVerifier;

class FluxAndMonoGeneratorServiceTest {

    FluxAndMonoGeneratorService service = new FluxAndMonoGeneratorService();

    /*
     * .create() is used to takes care of invoking the subscribe call which automatically.
     */
    @Test
    void generateNamesFlux() {
        // given

        // when
        var namesFlux = service.namesFlux();

        // then
        StepVerifier.create(namesFlux)
                .expectNext("John", "Alice", "Bob", "Charlie", "David")
                .verifyComplete();

        StepVerifier.create(namesFlux)
                .expectNextCount(5)
                .verifyComplete();

        StepVerifier.create(namesFlux)
                .expectNext("John")
                .expectNextCount(4)
                .verifyComplete();
    }

    @Test
    void namesFluxWithMap() {
        // given

        // when
        var namesFlux = service.namesFluxWithMap();

        // then
        StepVerifier.create(namesFlux)
                .expectNext("JOHN", "ALICE", "BOB", "CHARLIE", "DAVID")
                .verifyComplete();
    }

    @Test
    @DisplayName("NamesFlux should not change when mapping to uppercase")
    void namesFluxImmutability() {
        // given

        // when
        var namesFlux = service.namesFluxImmutability();

        // then
        StepVerifier.create(namesFlux)
                .expectNext("John", "Alice", "Bob", "Charlie", "David")
                .verifyComplete();
    }
}