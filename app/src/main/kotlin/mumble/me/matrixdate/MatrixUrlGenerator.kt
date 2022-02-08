package mumble.me.matrixdate

class MatrixUrlGenerator(private val initializer: MatrixUrlGenerator.() -> Unit) {

    lateinit var dailyPickedHHmm: String
    var url = "https://matrix.onkakao.net/apm/dashboard/projects/1134/applications/6362?start=now-\$before&end=now-\$afterMin}m&interval=0"
    var count: Int = 1
    var about: Int = 30

    fun build(): List<String> {
        initializer()

        return MatrixDateCalculator.calc(
            url, dailyPickedHHmm, count, about
        )
    }
}