package jp.ac.mayoi.util

import jp.ac.mayoi.core.util.parseIso8601
import org.junit.Assert.assertEquals
import org.junit.Test

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class DateParseTest {

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