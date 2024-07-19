package com.learnreactiveprogramming.service;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

public class FluxAndMonoGeneratorService {

    public Flux<String> namesFlux() {
        return Flux.fromIterable(List.of("John", "Alice", "Bob", "Charlie", "David"))
                .log();
    }

    public Mono<String> nameMono() {
        return Mono.just("John");
    }

    public static void main(String[] args) {
        FluxAndMonoGeneratorService service = new FluxAndMonoGeneratorService();

        System.out.println(" ----- Flux ------ ");

        service.namesFlux().subscribe(System.out::println);

        System.out.println(" ----- Mono ------ ");

        service.nameMono().subscribe(System.out::println);
    }

}
