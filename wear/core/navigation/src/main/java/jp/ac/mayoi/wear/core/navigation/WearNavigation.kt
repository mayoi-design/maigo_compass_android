package jp.ac.mayoi.wear.core.navigation

import kotlinx.serialization.Serializable

@Serializable
data class TripNavigation(
    val destinationLat: Double,
    val destinationLng: Double,
)

@Serializable
data object WatchWaitNavigation

@Serializable
data object SettingsNavigation


