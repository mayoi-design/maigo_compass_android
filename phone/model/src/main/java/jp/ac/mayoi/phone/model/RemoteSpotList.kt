package jp.ac.mayoi.phone.model

import kotlinx.serialization.Serializable

@Serializable
data class RemoteSpotList(
    val spots: List<RemoteSpot>
)