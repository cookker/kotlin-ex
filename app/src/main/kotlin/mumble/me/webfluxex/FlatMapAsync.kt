package mumble.me.webfluxex

import reactor.core.publisher.Flux
import reactor.core.scheduler.Schedulers.parallel

class FlatMapAsync {

    val generateAlphabet = Flux.just("a", "b", "c", "d", "e", "f", "g", "h", "i")

    fun slow() {
        generateAlphabet
            .window(3)
            .flatMap { fluxOf3Letters ->
                fluxOf3Letters.map (this::toUpperCase)
            }
            .doOnNext (::println)
            .blockLast()
    }

    fun subParallel() {
        generateAlphabet
            .window(3)
            .flatMap { fluxOf3Letters ->
                fluxOf3Letters.map(this::toUpperCase)
                    .subscribeOn(parallel())
            }
            .doOnNext(::println)
            .blockLast()
    }

    fun sequencial() {
        generateAlphabet
            .window(3)
            .flatMapSequential { fluxOf3Letters ->
                fluxOf3Letters.map(this::toUpperCase)
                    .subscribeOn(parallel())
            }
            .doOnNext(::println)
            .blockLast()
    }

    private fun toUpperCase(s: String): List<String> {
        Thread.sleep(1000)
        return listOf(s.uppercase(), Thread.currentThread().name)
    }
}

fun main() {
    with(FlatMapAsync()) {
//        slow()
        subParallel()
        sequencial()
    }
}