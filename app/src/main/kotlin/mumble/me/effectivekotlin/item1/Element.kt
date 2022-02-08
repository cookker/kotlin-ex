package mumble.me.effectivekotlin.item1

interface Element {
    val active: Boolean
}
class ActualElement: Element {
    override var active: Boolean = false // val을 var로 override할 수 있다.
}