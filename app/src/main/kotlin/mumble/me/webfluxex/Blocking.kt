package mumble.me.webfluxex

import reactor.core.publisher.Flux
import reactor.core.scheduler.Schedulers

class Blocking {

    fun blockingRepositoryToFlux() {
        val repository = BlockingRepository<User>()

        // blocking -> flux 변환
        val flux = Flux.defer { Flux.fromIterable(repository.findAll()) }
            .subscribeOn(Schedulers.boundedElastic())
    }

    fun fluxToBlockingRepository() {
        val flux = Flux.just(User("one"), User("two"), User("three"))
        val blockingRepository = BlockingRepository<User>()

        // subscribeOn()은 전체에 영향
        // publishOn()은 이후 처리에만 영향
        flux.publishOn(Schedulers.boundedElastic())
            .doOnNext(blockingRepository::save)
            .then() // -> Mono<Void>로 리턴.
    }
}

class BlockingRepository<T> {
    fun findAll(): List<T> = listOf()
    fun save(t: T) {
    }
}

data class User(val name: String)
