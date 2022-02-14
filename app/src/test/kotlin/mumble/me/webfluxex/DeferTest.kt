package mumble.me.webfluxex

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import reactor.core.publisher.Mono
import reactor.test.StepVerifier

internal class DeferTest {

    @Test
    fun defer1() {
        StepVerifier.create(Defer().monoDeferEmpty())
            .expectNext("emptyList")
            .verifyComplete()
    }

    @Test
    fun defer2() {
        val nonEmptyList = Mono.defer { Mono.just(listOf("one", "two", "three", "four"))}

        val listElements = nonEmptyList.flatMapIterable { it }
            .switchIfEmpty(Mono.defer { Mono.just("nonEmptyList") })

        StepVerifier.create(listElements)
            .expectNext("one")
            .expectNext("two")
            .expectNext("three")
            .expectNext("four")
            .verifyComplete()
    }
}