package mumble.me.webfluxex

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import reactor.core.publisher.Flux
import reactor.core.publisher.UnicastProcessor
import reactor.test.StepVerifier

internal class BackPressureTest{

    @Test
    fun failAsyncInnerFusion() {
        val up = UnicastProcessor.create<Int?>()
        StepVerifier.create(Flux.just(1)
            .hide()
            .flatMap({ up }, 1)
        )
            .then { up.onNext(1) }
            .then { up.onNext(2) }
            .then { up.onError(Exception("test")) }
            .expectNext(1, 2)
            .verifyErrorMessage("test")
    }
}