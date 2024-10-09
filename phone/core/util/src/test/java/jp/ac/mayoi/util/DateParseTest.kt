package jp.ac.mayoi.util

import org.junit.Assert.assertEquals
import org.junit.Test
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class DateParseTest {
    private fun parseIso8601(
        dateTime: String?,
    ): String {
        if (dateTime == null) {
            return "Unknown Date"
        } else {
            try {
                val res = LocalDateTime
                    .parse(
                        dateTime,
                        DateTimeFormatter.ISO_OFFSET_DATE_TIME
                    )
                    .format(
                        DateTimeFormatter.ofPattern("yyyy/MM/dd")
                    )

                return res
            } catch (_: Exception) {
                return "Unknown Date"
            }
        }
    }

    @Test
    fun can_parse_iso8601() {
        val createdAt = "2024-10-09T23:31:15+09:00"
        val actual = parseIso8601(createdAt)

        assertEquals("2024/10/09", actual)
    }

    @Test
    fun invalid_iso8601() {
        val createdAt = "2024-10-09T23:31:15+0900"
        val actual = parseIso8601(createdAt)

        assertEquals("Unknown Date", actual)
    }

    @Test
    fun date_is_null() {
        val createdAt = null
        val actual = parseIso8601(createdAt)

        assertEquals("Unknown Date", actual)
    }
}