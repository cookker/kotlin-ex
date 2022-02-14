package mumble.me.funtionalex

class InvokeAndCount<in T, out R>(private val f: (T) -> R) : (T) -> R {
    private var _count = 0
    val count: Int get() = _count

    override fun invoke(x: T): R = f(x).also { _count++ }
}

fun main() {
    val square = InvokeAndCount {
        i:Int -> i * i
    }

    (1..5).forEach {
        println("square of $it = ${square(it)}")
    }

    println("function was called ${square.count}")
}
