package jp.ac.mayoi.phone.model

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonNames

@Serializable
@OptIn(ExperimentalSerializationApi::class)
data class AdapterResisterReach(
    @JsonNames("spot_id") val spotId: Int,
    @JsonNames("user_id") val userId: String,
)