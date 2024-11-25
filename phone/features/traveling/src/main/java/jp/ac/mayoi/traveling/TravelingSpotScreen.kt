package jp.ac.mayoi.traveling

import android.content.Context
import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.android.gms.wearable.CapabilityClient
import com.google.android.gms.wearable.Wearable
import jp.ac.mayoi.common.model.RemoteSpotShrink
import jp.ac.mayoi.common.model.RemoteSpotShrinkList
import jp.ac.mayoi.core.resource.MaigoCompassTheme
import jp.ac.mayoi.core.resource.spacingDouble
import jp.ac.mayoi.core.util.SpotCard
import jp.ac.mayoi.phone.model.LocalSpot
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotlin.coroutines.cancellation.CancellationException

@Composable
internal fun TravelingSpotScreen(
    spotList: ImmutableList<LocalSpot>,
    onTripCancelButtonClick: () -> Unit,
) {
    val sendSpot = remoteSpotShrinkChange(spotList)
    val sendString = Json.encodeToString(sendSpot)

    sendLocalSpot("/location-data", sendString, LocalContext.current)

    Box(modifier = Modifier.fillMaxSize()) {
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(spacingDouble),
            contentPadding = PaddingValues(
                top = spacingDouble,
                bottom = 92.dp,
            )
        ) {
            items(spotList) { spot ->
                Row(
                    modifier = Modifier
                        .padding(horizontal = spacingDouble),
                ) {
                    SpotCard(
                        spot = spot,
                        onCardClicked = {},
                        isClickEnabled = false,
                        modifier = Modifier
                            .shadow(
                                elevation = 4.dp,
                                shape = RoundedCornerShape(16.dp)
                            )
                            .fillMaxWidth()
                    )
                }
            }
        }
        Box(
            modifier = Modifier
                .align(Alignment.BottomCenter)
        ) {
            TripCancelButton(
                onTripCancelButtonClick = onTripCancelButtonClick,
            )
        }
    }
}

private fun remoteSpotShrinkChange(localSpot: ImmutableList<LocalSpot>) : RemoteSpotShrinkList {
    val newList = mutableListOf<RemoteSpotShrink>()
    for (item in localSpot) {
        val tmp = RemoteSpotShrink(item.lat.toDouble(),item.lng.toDouble(),item.message)
        newList.add(tmp)
    }
    return RemoteSpotShrinkList(newList)
}

private fun sendLocalSpot(dataPath:String,payload:String,context: Context){
    val messageClient by lazy { Wearable.getMessageClient(context) }
    val capabilityClient by lazy { Wearable.getCapabilityClient(context) }
    //ここでClient作るのが違いまして、、、もっと上層で作らないといけないしviewmodelかserviceで管理しないとだめ?

    val scope = CoroutineScope(SupervisorJob() + Dispatchers.Main.immediate)

    scope.launch {
        while(true){
            try {
                Log.d("Message", "Message send start")
                val nodes = capabilityClient
                    .getCapability("wear", CapabilityClient.FILTER_REACHABLE)
                    .await()
                    .nodes
                Log.d("Message",nodes.toString())
                nodes.map { node ->
                    async {
                        Log.d("Message", node.id)
                        messageClient.sendMessage(node.id, dataPath, payload.toByteArray(Charsets.UTF_8))
                            .await()
                    }
                }.awaitAll()

                Log.d("Message", "send end")
            } catch (cancellationException: CancellationException) {
                Log.d("Message", "Message cancel")
                //throw cancellationException
            } catch (exception: Exception) {
                Log.d("Message", "Message failed")
            } finally {
                delay(5000)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun TripPreview() {
    MaigoCompassTheme {
        val spot = LocalSpot(
            lat = 0.0F,
            lng = 0.0F,
            message = "Hello From Preview!",
            imageUrl = "",
            spotId = "",
            reachedCount = 100,
            createdAt = "2024-10-09T23:31:15+09:00",
        )
        val rankingTestList: ImmutableList<LocalSpot> =
            List(10) {
                spot
            }.toImmutableList()
        TravelingSpotScreen(
            spotList = rankingTestList,
            onTripCancelButtonClick = { },
        )
    }
}