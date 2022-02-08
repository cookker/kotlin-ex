package mumble.me.effectivekotlin.item1

class Item1 {

    fun calculate(): Int {
        println("Calculating... ")
        return 42
    }

    val fizz = calculate()
    val buzz
        get() = calculate()

    fun go() {
        println(fizz)
        println(fizz)
        println(buzz)
        println(buzz)
    }
}

fun main() {
    Item1().go()
}
