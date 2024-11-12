package jp.ac.mayoi.wear.core.application.location

import android.location.Location

fun doubleToLocation(lng: Double, lat: Double): Location {
    val location = Location(null)
    location.latitude = lat
    location.longitude = lng

    return location
}