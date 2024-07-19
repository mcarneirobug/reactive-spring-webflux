package com.learnreactiveprogramming.service;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.List;
import java.util.Random;
import java.util.function.Function;
import java.util.function.UnaryOperator;
import java.util.stream.Collectors;

public class FluxAndMonoGeneratorService {

    private static final List<String> names = List.of("John", "Alice", "Bob", "Charlie", "David");
    private static final Random RANDOM      = new Random();

    public Flux<String> namesFlux() {
        return Flux.fromIterable(names)
                .log();
    }

    public Mono<String> nameMono() {
        return Mono.just(names.get(0));
    }

    public Mono<List<String>> namesMono_flatMap(int stringLength) {
        return Mono.just("alex")
                .map(String::toUpperCase)
                .filter(name -> name.length() > stringLength)
                .flatMap(this::splitStringMono)
                .log(); // Mono<List of A, L, E, X>
    }

    private Mono<List<String>> splitStringMono(String s) {
        var charList = s.chars()
                .mapToObj(Character::toString) // convert each char code to a String
                .collect(Collectors.toList());
        return Mono.just(charList);
    }

    public Flux<String> namesFluxWithMap() {
        return namesFlux()
               .map(String::toUpperCase)
                .log();
    }

    public Flux<String> namesFluxImmutability() {
        var namesFluxImmutability = Flux.fromIterable(names);

        namesFluxImmutability.map(String::toUpperCase); // This will not change the original flux

        return namesFluxImmutability;
    }

    public Flux<String> namesFluxWithFilter(int stringLength) {
        return namesFlux()
                .map(String::toUpperCase)
                .filter(name -> name.length() > stringLength)
                .map(name -> name.length() + "-" + name)
                .log();
    }

    public Flux<String> namesFluxWithFlatMap(int stringLength) {
        return namesFlux()
                .map(String::toUpperCase)
                .filter(name -> name.length() > stringLength)
                .flatMap(this::splitString)
                .log();
    }

    /*
     * This version of the method uses a transform function
     * can extract a functionality and assign that functionality to variable.
     * Is being used across our project, then just use transform operator along with the function.
     */
    public Flux<String> namesFluxWithTransform(int stringLength) {
        UnaryOperator<Flux<String>> filterMap = name -> name.map(String::toUpperCase)
                .filter(s -> s.length() > stringLength);

        return namesFlux()
                .transform(filterMap)
                .flatMap(this::splitString)
                .log();
    }

    /*
     * This version of the method includes a delay of a random amount of time, simulating network latency.
     * FlatMap used for asynchronous processing, and made One to N transformation.
     * Not preserve the ordering sequencing of elements.
     */
    public Flux<String> namesFluxWithFlatMapAsync(int stringLength) {
        return namesFlux()
                .map(String::toUpperCase)
                .filter(name -> name.length() > stringLength)
                .flatMap(this::splitStringWithDelay)
                .log();
    }

    /*
     * Preserve the ordering sequencing of elements.
     * Take a plenty of time to process all elements rather than the flatMap version.
     */
    public Flux<String> namesFluxWithConcatMapAsync(int stringLength) {
        return namesFlux()
                .map(String::toUpperCase)
                .filter(name -> name.length() > stringLength)
                .concatMap(this::splitStringWithDelay)
                .log();
    }

    // ALEX -> A, L, E, X
    public Flux<String> splitString(String name) {
        return Flux.fromArray(name.split(""));
    }

    public Flux<String> splitStringWithDelay(String name) {
        var delay = RANDOM.nextInt(1000);

        return Flux.fromArray(name.split(""))
                .delayElements(Duration.ofMillis(delay));
    }

    public static void main(String[] args) {
        FluxAndMonoGeneratorService service = new FluxAndMonoGeneratorService();

        System.out.println(" ----- Flux ------ ");

        service.namesFlux().subscribe(System.out::println);

        System.out.println(" ----- Mono ------ ");

        service.nameMono().subscribe(System.out::println);
    }

}
