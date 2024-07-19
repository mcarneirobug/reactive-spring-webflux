package com.learnreactiveprogramming.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import reactor.test.StepVerifier;

import java.util.List;

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

    @Test
    void namesFluxWithFilter() {
        // given
        int stringLength = 3;

        // when
        var namesFlux = service.namesFluxWithFilter(stringLength);

        // then
        StepVerifier.create(namesFlux)
                .expectNext("4-JOHN", "5-ALICE", "7-CHARLIE", "5-DAVID")
                .verifyComplete();
    }

    @Test
    void namesFluxWithFlatMap() {
        // given
        int stringLength = 6;

        // when
        var namesFlux = service.namesFluxWithFlatMap(stringLength);

        // then
        StepVerifier.create(namesFlux)
                .expectNext("C", "H", "A", "R", "L", "I", "E")
                .verifyComplete();
    }

    @Test
    void namesFluxWithFlatMapAsync() {
        // given
        int stringLength = 4;

        // when
        var namesFlux = service.namesFluxWithFlatMapAsync(stringLength);

        // then
        StepVerifier.create(namesFlux)
                .expectNextCount(17)
                .verifyComplete();
    }

    @Test
    void namesFluxWithConcatMapAsync() {
        // given
        int stringLength = 4;

        // when
        var namesFlux = service.namesFluxWithConcatMapAsync(stringLength);

        // then
        StepVerifier.create(namesFlux)
                .expectNextCount(17)
                .verifyComplete();

        StepVerifier.create(namesFlux)
                .expectNext("A", "L", "I", "C", "E", "C", "H", "A", "R", "L", "I", "E", "D", "A", "V", "I", "D")
                .verifyComplete();
    }

    @Test
    void namesMono_flatMap() {
        // given
        int stringLength = 3;

        // when
        var value = service.namesMono_flatMap(stringLength);

        // then
        StepVerifier.create(value)
                .expectNext(List.of("A", "L", "E", "X"))
                .verifyComplete();
    }

    @Test
    void namesFluxWithTransform() {
        // given
        int stringLength = 6;

        // when
        var namesFlux = service.namesFluxWithTransform(stringLength);

        // then
        StepVerifier.create(namesFlux)
                .expectNext("C", "H", "A", "R", "L", "I", "E")
                .verifyComplete();
    }

    @Test
    void namesFluxWithTransformWhenEmptyValues() {
        // given
        int stringLength = 8;

        // when
        var namesFlux = service.namesFluxWithTransform(stringLength);

        // then
        StepVerifier.create(namesFlux)
                .expectNext("default")
                .verifyComplete();
    }

    @Test
    void namesFluxWithTransformWhenUseSwitchIfEmptyValues() {
        // given
        int stringLength = 7;

        // when
        var namesFlux = service.namesFluxWithTransformSwitchIfEmpty(stringLength);

        // then
        StepVerifier.create(namesFlux)
                .expectNext("D", "E", "F", "A", "U", "L", "T")
                .verifyComplete();
    }

    @Test
    void namesFluxWithConcat() {
        // given

        // when
        var concatFlux = service.namesFluxWithConcat();

        // then
        StepVerifier.create(concatFlux)
               .expectNext("A", "B", "C", "D", "E", "F")
               .verifyComplete();
    }

    @Test
    void namesFluxMerge() {
        // given

        // when
        var mergedFlux = service.namesFluxMerge();

        // then
        StepVerifier.create(mergedFlux)
               .expectNext("A", "D", "B", "E", "C", "F")
               .verifyComplete();
    }

    @Test
    void namesFluxMergeSequential() {
        // given

        // when
        var mergedFluxSequential = service.namesFluxMergeSequential();

        // then
        StepVerifier.create(mergedFluxSequential)
                .expectNext("A", "B", "C", "D", "E", "F")
                .verifyComplete();
    }
}