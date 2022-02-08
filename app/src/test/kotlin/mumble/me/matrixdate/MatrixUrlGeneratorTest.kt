package mumble.me.matrixdate

import org.junit.jupiter.api.Test

internal class MatrixUrlGeneratorTest {

    @Test
    fun `something to do`() {
        MatrixUrlGenerator {
            dailyPickedHHmm = "0750"
            count = 10
            about = 60
        }.build().onEach {
            println("$it")
        }
    }
}