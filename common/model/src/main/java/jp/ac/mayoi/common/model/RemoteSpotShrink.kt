package jp.ac.mayoi.common.model

import android.location.Location

data class RemoteSpotShrink(
    val lat: Double,
    val lng: Double,
    val comment: String,
    // val emoji: なんか
) {
    val location: Location
        get() = Location(null).also {
            it.latitude = this.lat
            it.longitude = this.lng
        }
}
