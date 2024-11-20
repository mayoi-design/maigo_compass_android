package jp.ac.mayoi.phone.model

data class LocalSpot(
    val lat: Float,
    val lng: Float,
    val message: String,
    val imageUrl: String,
    val spotId: String,
    val reachedCount: Int?,
    val createdAt: String?,
)