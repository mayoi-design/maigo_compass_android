package jp.ac.mayoi.common.model

import android.location.Location
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.descriptors.listSerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

@Serializable
data class RemoteSpotShrink(
    val lat: Double,
    val lng: Double,
    val comment: String,
    // val emoji: なんか
) {
    val location: Location
        get() = Location(null).also {
            it.latitude = this.lat
            it.longitude = this.lng
        }
}

@Serializable
data class RemoteSpotShrinkList(
    @Serializable(with = RemoteSpotShrinkListSerializer::class)
    val spots: List<RemoteSpotShrink>
)


object RemoteSpotShrinkListSerializer : KSerializer<List<RemoteSpotShrink>> {

    private val dataSerializer = RemoteSpotShrink.serializer()

    @OptIn(ExperimentalSerializationApi::class)
    override val descriptor = listSerialDescriptor(dataSerializer.descriptor)

    override fun serialize(encoder: Encoder, value: List<RemoteSpotShrink>) =
        ListSerializer(dataSerializer).serialize(encoder, value)

    override fun deserialize(decoder: Decoder): List<RemoteSpotShrink> =
        ListSerializer(dataSerializer).deserialize(decoder)

}
