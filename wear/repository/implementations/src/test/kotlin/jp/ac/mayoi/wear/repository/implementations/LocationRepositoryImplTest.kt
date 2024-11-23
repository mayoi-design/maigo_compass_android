package jp.ac.mayoi.wear.repository.implementations

import org.junit.Assert.assertEquals
import org.junit.Test

@Suppress("NonAsciiCharacters", "TestFunctionName")
class LocationRepositoryImplTest {

    @Test
    fun `365から函館駅へ`() {
        // 365
        val currentLat = 41.842116783883995
        val currentLng = 140.76710646276334

        // 函館駅
        val destinationLat = 41.773674691098705
        val destinationLng = 140.72677811248317
        val currentHeading = 0.0

        // https://www.wolframalpha.com/input?i2d=true&i=atan2%5C%2840%2941.773674691098705+-+41.842116783883995%5C%2844%29+140.72677811248317+-+140.76710646276334%5C%2841%29+*+Divide%5B180%2CPI%5D+-+90+mod+360
        // 365室前から真北を向いた時、函館駅は南南西方面にある
        val expected = 149.49199786282
        val actual = LocationRepositoryImpl()
            .getBearing(
                currentLat = currentLat,
                currentLng = currentLng,
                destinationLat = destinationLat,
                destinationLng = destinationLng,
                currentHeading = currentHeading,
            )

        assertEquals(expected, actual, 0.0001)
    }

    @Test
    fun 函館駅から湯倉神社へ() {
        // 函館駅
        val currentLat = 41.773674691098705
        val currentLng = 140.72677811248317

        // 湯倉神社
        val destinationLat = 41.781998336369114
        val destinationLng = 140.79100220973095

        val currentHeading = 0.0

        // https://www.wolframalpha.com/input?i2d=true&i=atan2%5C%2840%2941.781998336369114+-+41.773674691098705%5C%2844%29+140.79100220973095+-+140.72677811248317%5C%2841%29+*+Divide%5B180%2CPI%5D+-+90+mod+360
        // 函館駅前から真北を向いた時、湯倉神社は東北東方面にある
        val expected = 277.38455101414
        val actual = LocationRepositoryImpl()
            .getBearing(
                currentLat = currentLat,
                currentLng = currentLng,
                destinationLat = destinationLat,
                destinationLng = destinationLng,
                currentHeading = currentHeading,
            )

        assertEquals(expected, actual, 0.0001)
    }

    @Test
    fun 亀田支所前から昭和ターミナル方面を向いた時の函館空港() {
        // 亀田支所前
        val currentLat = 41.815447973759994
        val currentLng = 140.75185069083656

        // 函館空港
        val destinationLat = 41.77596538110248
        val destinationLng = 140.81591755754837

        // 昭和ターミナル方面を向く ()
        val currentHeading = 330.0

        // https://www.wolframalpha.com/input?i2d=true&i=atan2%5C%2840%2941.781998336369114+-+41.773674691098705%5C%2844%29+140.79100220973095+-+140.72677811248317%5C%2841%29+*+Divide%5B180%2CPI%5D+-+90+mod+360
        // 亀田支所前から昭和ターミナル方面を向いた時、函館空港はほぼ真後ろにあるはず
        val expected = 277.38455101414
        val actual = LocationRepositoryImpl()
            .getBearing(
                currentLat = currentLat,
                currentLng = currentLng,
                destinationLat = destinationLat,
                destinationLng = destinationLng,
                currentHeading = currentHeading,
            )

        assertEquals(expected, actual, 0.0001)
    }
}