package jp.ac.mayoi.phone.model

import kotlinx.serialization.Serializable

@Serializable
data class RemoteRanking(
    val spots: List<RemoteSpot>
)
