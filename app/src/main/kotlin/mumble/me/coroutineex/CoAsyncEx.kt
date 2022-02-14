package mumble.me.coroutineex

import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlin.system.measureTimeMillis

class CoAsyncEx {

    suspend fun task1(): Int {
        delay(3000)
        return 10
    }

    suspend fun task2(): Int {
        delay(5000)
        return 20
    }

    suspend fun doTask() = coroutineScope {
        val result1 = async { task1() }
        val result2 = async { task2() }
        result1.await() + result2.await()
    }
}

suspend fun main() {
    val measureTimeMillis = measureTimeMillis {
        println(CoAsyncEx().doTask())
    }

    println("measureTimeMillis = $measureTimeMillis")
}