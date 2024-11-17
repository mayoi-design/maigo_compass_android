package jp.ac.mayoi.wear.repository.interfaces

import android.location.Location

interface LocationRepository {
    fun getBearing(current: Location, destination: Location, currentHeading: Double): Double
}