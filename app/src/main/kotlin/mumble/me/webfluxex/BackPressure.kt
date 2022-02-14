package mumble.me.webfluxex

import org.reactivestreams.Subscription
import reactor.core.publisher.*
import java.util.concurrent.CompletableFuture


class BackPressure {

    fun fromMonoToCompletableFuture(mono: Mono<String>): CompletableFuture<String> =
        mono.toFuture()

    fun fromCompletableFuture(future: CompletableFuture<String>): Mono<String> =
        Mono.fromFuture(future)

    fun exBackPressure() {
        Flux.range(1, 50)
            .subscribe(
                { println("$it") },
                { error("${it.printStackTrace()}") },
                { println("completed") },
                { subscription ->
                    for(i in 1..5) {
                        println("request next 10 elements")
                        subscription.request(10)
                    }
                }
            )
    }

    fun exBackPressure2() {
        Flux.range(1, 50)
            .subscribeWith(object : BaseSubscriber<Int>() {
                override fun hookOnSubscribe(subscription: Subscription) {
                    request(10)
                }

                override fun hookOnNext(value: Int) {
                    request(10)
                    println(value)
                }

                override fun hookFinally(type: SignalType) {
                    println("=======")
                }
            })
    }

    fun exBackPressure3() {
        val buffer = Sinks.many().multicast().onBackpressureBuffer<Int>()
        for(i in 1..100) {
            buffer.tryEmitNext(i)
        }
        buffer.asFlux()
            .doOnNext { println(">> $it") }
            .subscribe()

        Flux.range(1, 50)
            .hide()
            .flatMapSequential { Flux.range(it, 2) }
            .doOnNext { println("$it") }
            .subscribeWith(TestSubscriber())
    }

    fun exFluxHide() {
        Flux.merge(Flux.just(1),
            Flux.range(2, 2),
            Flux.just(4, 5, 6)
                .hide()
        )
            .doOnNext { println("$it") }
            .subscribe();
    }
}

class TestSubscriber : BaseSubscriber<Int>()

fun main() {
    with(BackPressure()) {
//        exBackPressure()
//        exBackPressure2()
//        exBackPressure3()
        exFluxHide()
    }
}