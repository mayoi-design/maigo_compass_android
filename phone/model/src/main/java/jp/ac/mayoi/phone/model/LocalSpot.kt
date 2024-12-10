package jp.ac.mayoi.phone.model

import jp.ac.mayoi.common.model.RemoteSpotShrink

data class LocalSpot(
    val lat: Float,
    val lng: Float,
    val message: String,
    val imageUrl: String,
    val spotId: String,
    val reachedCount: Int?,
    val createdAt: String?,
) {
    constructor(remote: RemoteSpot) : this(
        lat = remote.lat,
        lng = remote.lng,
        message = remote.message,
        imageUrl = remote.imageUrl,
        spotId = remote.spotId,
        reachedCount = remote.reachedCount,
        createdAt = remote.createdAt,
    )

    fun toRemoteSpotShrink(): RemoteSpotShrink {
        return RemoteSpotShrink(
            lat = this.lat.toDouble(),
            lng = this.lng.toDouble(),
            comment = this.message,
        )
    }
}