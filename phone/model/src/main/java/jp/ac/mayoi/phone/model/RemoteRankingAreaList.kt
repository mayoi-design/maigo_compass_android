package jp.ac.mayoi.phone.model

import kotlinx.serialization.Serializable

@Serializable
data class RemoteRankingAreaList(
    val areas: List<RemoteRankingArea>
)