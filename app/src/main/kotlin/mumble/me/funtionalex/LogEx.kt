package mumble.me.funtionalex

import mu.KotlinLogging



class LogEx {
    fun logging() {
        val add4 = { i: Int -> i + 4 }
        val func = add4.log()

        println(func(2) + 3)
    }

    fun some(): Int {
        return 10
    }
}

fun <T, R> ((T) -> R).log(): (T) -> R =
    {
        p ->
        this(p).also { println("param = $p, result= $it") }
    }

fun main() {
    with(LogEx()) {
        logging()
    }

    LogEx().some().info()
}

fun <T> T.info() {
    val log = KotlinLogging.logger {}
    log.info { this }
}

/*
* public inline fun <T> T.also(block: (T) -> Unit): T {
    contract {
        callsInPlace(block, InvocationKind.EXACTLY_ONCE)
    }
    block(this)
    return this
}
* */
