package com.learnreactiveprogramming.service;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

public class FluxAndMonoGeneratorService {

    private static final List<String> names = List.of("John", "Alice", "Bob", "Charlie", "David");

    public Flux<String> namesFlux() {
        return Flux.fromIterable(names)
                .log();
    }

    public Mono<String> nameMono() {
        return Mono.just(names.get(0));
    }

    public Flux<String> namesFluxWithMap() {
        return namesFlux()
               .map(String::toUpperCase)
                .log();
    }

    public static void main(String[] args) {
        FluxAndMonoGeneratorService service = new FluxAndMonoGeneratorService();

        System.out.println(" ----- Flux ------ ");

        service.namesFlux().subscribe(System.out::println);

        System.out.println(" ----- Mono ------ ");

        service.nameMono().subscribe(System.out::println);
    }

}
