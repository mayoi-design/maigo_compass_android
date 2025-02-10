package jp.ac.mayoi.phone.model.enums

import org.junit.Assert.assertEquals
import org.junit.Test

class ServiceAreaParseTest {

    // 何かenumを一項目追加すると落ちるテスト
    // サーバー側でenumを追加した際は、ここで文字列の確認を行う
    // remoteに入れる文字列は必ずswaggerの返り値からコピペすること
    @Test
    fun can_parse_server_area() {
        val remote = listOf("ekimae", "bay", "hakodateyama", "goryokaku", "yunokawa", "mihara")
        val actual = remote.map { ServiceAreaAdapter.fromRemote(it) }

        val expected = ServiceAreaAdapter.ServiceArea.entries.toTypedArray()
            .filter { it.remoteToken != "UNKNOWN" }
        assertEquals(expected, actual)
    }

    @Test
    fun invalid_remote_token() {
        val remote = "invalid"
        val actual = ServiceAreaAdapter.fromRemote(remote)

        assertEquals(ServiceAreaAdapter.ServiceArea.UNKNOWN, actual)
    }
}