package mumble.me.effectivekotlin.item2

class PrimeNumber {

    val primes: Sequence<Long> = sequence {
        var numbers = generateSequence(2L) { it + 1 }

        while (true) {
            val prime = numbers.first()
            yield(prime)
            numbers = numbers.drop(1)
                .filter { it % prime != 0 }
        }
    }

    fun printPrime(n: Int) {
        println(primes.take(n).toList())
    }
}

fun main() {
    PrimeNumber().printPrime(10)
}
