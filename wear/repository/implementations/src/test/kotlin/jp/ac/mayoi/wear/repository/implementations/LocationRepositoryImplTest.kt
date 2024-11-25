package jp.ac.mayoi.wear.repository.implementations

import org.junit.Assert.assertEquals
import org.junit.Test

@Suppress("NonAsciiCharacters", "TestFunctionName")
class LocationRepositoryImplTest {
    // テストケースの作り方
    // 1. 現在地と目的地と向いている方位角を決める
    // 2. 方位角を方位系から数学系に直す
    // 3. (x, y) = (1, 0)を回転行列を用いて2で求めた数学系の角度だけ回す
    //   3.1. 新しい点の位置を ax, ay とする
    // 4. 目的地に向かうベクトルを計算し、その座標をbx, byとする
    // 5. θ = acos((ax*bx + ay*by) / (sqrt(ax**2 + ay**2)*sqrt(bx**2 + by**2))) を計算する
    //   5.1 偏角のうち小さい方がθになるので、適宜外角に直したりする

    @Test
    fun `365から函館駅へ`() {
        // 365
        val currentLat = 41.842116783883995
        val currentLng = 140.76710646276334

        // 函館駅
        val destinationLat = 41.773674691098705
        val destinationLng = 140.72677811248317
        val currentHeading = 0.0

        // 365室前から真北を向いた時、函館駅は南南西方面にある
        val expected = 149.49199786282134
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

        // 昭和ターミナル方面を向く (数学系では120°)
        val currentHeading = 330.0

        // 亀田支所前から昭和ターミナル方面を向いた時、函館空港は南南東か南東にあるはず
        val expected = 360.0 - 151.6443253181512
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