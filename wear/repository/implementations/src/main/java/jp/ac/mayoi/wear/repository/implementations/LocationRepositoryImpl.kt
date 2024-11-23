package jp.ac.mayoi.wear.repository.implementations

import android.location.Location
import android.util.Log
import jp.ac.mayoi.wear.repository.interfaces.LocationRepository
import kotlin.math.atan2

class LocationRepositoryImpl : LocationRepository {
    override fun getBearing(
        current: Location,
        destination: Location,
        currentHeading: Double,
    ): Double {
        val bearing = getBearing(
            currentLat = current.latitude,
            currentLng = current.longitude,
            destinationLat = destination.latitude,
            destinationLng = destination.longitude,
            currentHeading = currentHeading,
        )

        Log.d(
            "LocationRepository",
            "CurrentLocation: (lat = ${current.latitude}, lng = ${current.longitude}), " +
                    "Destination: (lat = ${destination.latitude}, lng = ${current.longitude}), " +
                    "HeadingTo: $currentHeading, Bearing: $bearing"
        )

        return bearing
    }

    internal fun getBearing(
        currentLat: Double,
        currentLng: Double,
        destinationLat: Double,
        destinationLng: Double,
        currentHeading: Double,
    ): Double {
        val convertedLatitude = destinationLat - currentLat
        val convertedLongitude = destinationLng - currentLng
        val thetaInRad = atan2(convertedLatitude, convertedLongitude)
        val theta = Math.toDegrees(thetaInRad).mod(360.0)

        return (theta - 90.0 + currentHeading).mod(360.0)
    }
}