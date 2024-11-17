package jp.ac.mayoi.wear.repository.implementations

import android.location.Location
import jp.ac.mayoi.wear.repository.interfaces.LocationRepository
import kotlin.math.atan2

class LocationRepositoryImpl : LocationRepository {
    override fun getBearing(
        current: Location,
        destination: Location,
        currentHeading: Double,
    ): Double {
        val convertedLatitude = destination.latitude - current.latitude
        val convertedLongitude = destination.longitude - current.longitude
        // Log.d("getHeaddingTo", "converted: ($convertedLatitude, $convertedLongitude)")
        val thetaInRad = atan2(convertedLatitude, convertedLongitude)
        // Log.d("getHeddingTo", "phiInRad: $thetaInRad")
        val theta = Math.toDegrees(thetaInRad).mod(360.0)
        // Log.d("getHeddingTo", "headTo: $theta")

        return (theta - currentHeading).mod(360.0)
    }
}