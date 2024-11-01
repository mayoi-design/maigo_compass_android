package jp.ac.mayoi.phone.model

data class UserMemory(
    val totalTripCount: Int,
    val spotShareCount: Int,
    val areaShareCounts: List<UserMemory>,
    val exploreSpotCount: Int,
    val firstTripDate: String?,
    val latestTripDate: String?,
)