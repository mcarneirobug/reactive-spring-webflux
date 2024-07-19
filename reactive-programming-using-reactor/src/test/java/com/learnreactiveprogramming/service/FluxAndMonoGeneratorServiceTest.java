package com.learnreactiveprogramming.service;

import org.junit.jupiter.api.Test;
import reactor.test.StepVerifier;

import static org.junit.jupiter.api.Assertions.*;

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
}