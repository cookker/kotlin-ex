package mumble.me.coroutineex

import kotlinx.coroutines.*

class CoroutineScopeEx {

    fun some() {
        val scope = CoroutineScope(CoroutineName("Parent"))
        if( scope.coroutineContext[Job] != null ) {
            println("New job is created!")
        }

        scope.launch {
            launch(CoroutineName("Child-A")) {
                delay(1000)
                println("${Thread.currentThread().name} throwing exception")
                throw MyException()
            }

            launch(CoroutineName("Child-B")) {
                println("${Thread.currentThread().name} before exception...")
                delay(5000)
                println("${Thread.currentThread().name} after exception...")
            }
        }
    }
}

class MyException : RuntimeException()

fun main() {
    CoroutineScopeEx().some()
    // https://blog.burt.pe.kr/posts/everything-you-need-to-know-about-kotlin-coroutines/
    // 이거보고 따라 쳤는데 결과가 다르게 나온다.
}