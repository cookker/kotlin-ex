package mumble.me.dataclassentity

import kotlin.reflect.KProperty1

// Entity
data class Student(
    val id: Long?,
    val name: String
)

data class Book(
    val id: Long?,
    val title: String
) {
    fun read() {
    }
}

