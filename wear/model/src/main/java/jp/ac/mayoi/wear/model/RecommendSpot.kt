package jp.ac.mayoi.wear.model

import jp.ac.mayoi.common.model.RemoteSpotShrink

data class RecommendSpot(
    val lat: Double,
    val lng: Double,
    val comment: String,
    // val emoji: なんか,
    val distance: Double,
    val bearing: Double,
) {
    constructor(remote: RemoteSpotShrink, distance: Double, bearing: Double) : this(
        lat = remote.lat,
        lng = remote.lng,
        comment = remote.comment,
        // emoji = remote.emoji,
        distance = distance,
        bearing = bearing,
    )
}
