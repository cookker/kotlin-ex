package mumble.me.coroutineex

import kotlinx.coroutines.*
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class CoroutineContextEx {

    suspend fun some() {
        // 1. create single thread dispatcher
        val ec: ExecutorService = Executors.newFixedThreadPool(5)
        // val ec: ExecutorService = Executors.newSingleThreadExecutor()
        val dispatcher: ExecutorCoroutineDispatcher = ec.asCoroutineDispatcher()

        // 2. bind coroutine scope with single threaded dispatcher
        val scope = CoroutineScope(dispatcher)
        var counter = 0
        val jobs = mutableListOf<Job>()

        fun incrementAsync() = scope.launch { counter++ }

        // 3. launch 1000 coroutines to increment counter
        repeat(1000) {
            jobs += incrementAsync()
        }

        // 4. wait for all the coroutines to finish
        jobs.joinAll()
        println("counter = $counter")
        ec.shutdown()
    }
}

suspend fun main() {
    with(CoroutineContextEx()) {
        some()
    }
}
