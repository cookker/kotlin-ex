package mumble.me.matrixdate

import mu.KotlinLogging
import java.time.Duration
import java.time.LocalDate
import java.time.LocalDateTime

private val log = KotlinLogging.logger {}

object MatrixDateCalculator {

    fun calc(url: String, dailyPickedHHmm: String, count: Int, about: Int = 30): List<String> {
        log.info { "dailyPickedDate=$dailyPickedHHmm" }
        val between = Duration.between(dailyPickedHHmm.toLocalDateTime(), LocalDateTime.now()).toMinutes().toInt()
        log.info { "between = ${between}min" }

        val basis = about / 2
        val beforeMin = between + basis
        val afterMin = beforeMin - about

        check(beforeMin > afterMin) {
            "beforeMin값이 afterMin값보다 큽니다."
        }

        val sequence = sequence {
            var period = 0
            while (true) {
                val urlWithParams =
                    "https://matrix.onkakao.net/apm/dashboard/projects/1134/applications/6362?start=now-${beforeMin + period}m&end=now-${afterMin + period}m&interval=0"
                period += 24 * 60
                yield(urlWithParams)
            }
        }

        return sequence.take(count).toList()
    }
}

fun String.toLocalDateTime(): LocalDateTime {

    require(this.isNotBlank()) { "cannot parse to LocalDateTime" }
    require(this.length == 4) { "invalid Time format" }

    val hour = this.take(2)
    val minute = this.drop(2)

    log.info { "hour:$hour, minute:$minute" }

    return LocalDate.now().atTime(hour.toInt(), minute.toInt())
}
