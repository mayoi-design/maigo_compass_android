package jp.ac.mayoi.common.model

import kotlinx.serialization.Serializable

@Serializable
data class Destination(
    val lat: Double,
    val lng: Double,
)