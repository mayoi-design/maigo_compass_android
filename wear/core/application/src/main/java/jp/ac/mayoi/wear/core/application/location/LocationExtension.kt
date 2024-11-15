package jp.ac.mayoi.wear.core.application.location

import android.location.Location

/**
 * Create a Location object from two Double values.
 * lng = Longitude (緯度), lat = Latitude (経度).
 */
fun doubleToLocation(lng: Double, lat: Double): Location {
    val location = Location(null)
    location.latitude = lat
    location.longitude = lng

    return location
}