package jp.ac.mayoi.phone.model

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonNames

@Serializable
@OptIn(ExperimentalSerializationApi::class)
data class AdapterResisterSpot(
    val lat: Double,
    val lng: Double,
    val message: String,
    @JsonNames("image_url") val imageUrl: String,
)
