package jp.ac.mayoi.phone.model

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonNames

@Serializable
@OptIn(ExperimentalSerializationApi::class)
data class RemoteSpot(
    val lat: Float,
    val lng: Float,
    val message: String,

    @JsonNames("image_url") @SerialName("image_url")
    val imageUrl: String,

    @JsonNames("post_user_id") @SerialName("post_user_id")
    val postUserId: String,

    @JsonNames("created_at") @SerialName("created_at")
    val createdAt: String,

    @JsonNames("reached_count") @SerialName("reached_count")
    val reachedCount: Int,

    @JsonNames("spot_id") @SerialName("spot_id")
    val spotId: Int,
)