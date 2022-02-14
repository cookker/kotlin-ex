package mumble.me.webfluxex

import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

class Defer {

    fun monoDeferEmpty(): Flux<String> {
        val emptyList: Mono<List<String>> = Mono.defer { Mono.just(listOf()) }

        val emptyListElements = emptyList.flatMapIterable { it }
            .switchIfEmpty(Mono.defer { Mono.just("emptyList") })
            .log()

        return emptyListElements
    }
}