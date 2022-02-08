package mumble.me.effectivekotlin.item4

open class Animal
class Zebra: Animal()

fun main() {

    var animal = Zebra()
//    animal = Animal() // type mismatch

    var animal2: Animal = Zebra()
    animal2 = Animal() // OK
}