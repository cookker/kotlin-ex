package mumble.me.effectivekotlin.item1

class Propertyex {
    val name: String? = "some"
    val surname: String = "thing"

    val fullname: String?
        get() = name?.let { "$it $surname" }

    val fullname2: String? = name?.let { "$it $surname" }

    fun go() {
//        if (fullname != null) {
//            println(fullname.length) // 오류.
//        }

        if(fullname2 != null){
            println(fullname2.length)
        }
    }
}