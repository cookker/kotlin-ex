package mumble.me.effectivekotlin.item1

import java.util.concurrent.TimeUnit
import kotlin.concurrent.thread

class MutableListEx {

    val list1: MutableList<Int> = mutableListOf()
    var list2: List<Int> = listOf()

    val list3: List<Int> = listOf() // y not

    fun test1() {
        var list = listOf<Int>()
        for( i in 1..1000) {
            thread {
                list = list + 1
            }
        }
        TimeUnit.MILLISECONDS.sleep(1000)
        println(list.size)
    }

    fun test2() {
        var list4 = mutableListOf<Int>() // 이렇게 하면 안 됨. 프라퍼티와 컬랙션 모두 변경가능한 지점이므로 두 지점에 대한 동기화를 구현해야 한다. 또한 모호성이 발생하여 += 을 사용할 수 없다.
        list4 += 1 // += 사용할 수 있는뎁?
        println(list4.size)
    }
}

fun main() {
    with(MutableListEx()) {
        test1()
        test2()
    }
}