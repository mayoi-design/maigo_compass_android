package jp.ac.mayoi.phone.model.enums

import org.junit.Assert.assertEquals
import org.junit.Test

class ServiceAreaParseTest {

    @Test
    fun can_parse_hakodate_st() {
        val remote = "ekimae"
        val actual = ServiceAreaAdapter.fromRemote(remote)

        assertEquals(ServiceAreaAdapter.ServiceArea.HAKODATE_STA, actual)
    }

    @Test
    fun invalid_remote_token() {
        val remote = "invalid"
        val actual = ServiceAreaAdapter.fromRemote(remote)

        assertEquals(ServiceAreaAdapter.ServiceArea.UNKNOWN, actual)
    }
}