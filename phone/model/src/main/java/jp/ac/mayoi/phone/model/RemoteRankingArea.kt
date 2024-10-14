package jp.ac.mayoi.phone.model

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonNames

@Serializable
@OptIn(ExperimentalSerializationApi::class)
data class RemoteRankingArea(
    @JsonNames("area_name") @SerialName("area_name") val areaName: String,
    @JsonNames("area_id") @SerialName("area_id") val areaId: String,
)

@Serializable
data class RankingAreaResponse(
    val areas: List<RemoteRankingArea>
)