package mumble.me.webfluxex

import org.reactivestreams.Subscription
import reactor.core.publisher.BaseSubscriber
import reactor.core.publisher.Flux
import reactor.core.scheduler.Schedulers
import java.util.concurrent.CountDownLatch

class SchedulerEx {

    fun some1() {
        val latch = CountDownLatch(1)

        Flux.range(1, 6)
            .map {
                println("[${Thread.currentThread().name}] map 1:$it")
                it + 10
            }
            .publishOn(Schedulers.newBoundedElastic(5, 10, "PUB"), 2)
            .map {
                println("[${Thread.currentThread().name}] map 2:$it")
                it + 10
            }
            .subscribeWith(object: BaseSubscriber<Int>(){
                override fun hookOnSubscribe(subscription: Subscription) {
                    println("[${Thread.currentThread().name}] hookOnSubscribe")
                    requestUnbounded()
                }

                override fun hookOnNext(value: Int) {
                    println("[${Thread.currentThread().name}] hookOnNext: $value")
                }

                override fun hookOnComplete() {
                    println("[${Thread.currentThread().name}] hookOnComplete")
                    latch.countDown()
                }
            })
    }

    fun some2() {
        val latch = CountDownLatch(1)

        Flux.range(1, 6)
            .publishOn(Schedulers.newBoundedElastic(1,10, "PUB1"), 2)
            .map {
                println("[${Thread.currentThread().name}] map 1:$it")
                it + 10
            }
            .publishOn(Schedulers.newBoundedElastic(1,10, "PUB2"))
            .map {
                println("[${Thread.currentThread().name}] map 2:$it")
                it + 10
            }
            .subscribeWith(object: BaseSubscriber<Int>(){
                override fun hookOnSubscribe(subscription: Subscription) {
                    println("[${Thread.currentThread().name}] hookOnSubscribe")
                    requestUnbounded()
                }

                override fun hookOnNext(value: Int) {
                    println("[${Thread.currentThread().name}] hookOnNext: $value")
                }

                override fun hookOnComplete() {
                    println("[${Thread.currentThread().name}] hookOnComplete")
                    latch.countDown()
                }
            })

        latch.await()
    }
}

fun main() {
    with(SchedulerEx()) {
        some1()
    }
}