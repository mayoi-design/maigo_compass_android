package jp.ac.mayoi.traveling

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
import androidx.compose.ui.unit.dp
import com.google.android.gms.wearable.CapabilityClient
import com.google.android.gms.wearable.MessageClient
import jp.ac.mayoi.core.resource.spacingDouble
import jp.ac.mayoi.core.util.SpotCard
import jp.ac.mayoi.phone.model.LocalSpot
import kotlinx.collections.immutable.ImmutableList
import org.koin.compose.koinInject

@Composable
internal fun TravelingSpotScreen(
    spotList: ImmutableList<LocalSpot>,
    onTripCancelButtonClick: () -> Unit,
    onSendLocalSpot: (String, ImmutableList<LocalSpot>, MessageClient, CapabilityClient) -> Unit,
) {
    val messageClient: MessageClient = koinInject()
    val capabilityClient: CapabilityClient = koinInject()
    val LOCATION_DATA = "/location-data"

    onSendLocalSpot(LOCATION_DATA, spotList, messageClient, capabilityClient)

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

//Preview不可能になってしまったどうしよう
//@Preview(showBackground = true)
//@Composable
//private fun TripPreview() {
//    val messageClient: MessageClient = koinInject()
//    val capabilityClient: CapabilityClient = koinInject()
//    MaigoCompassTheme {
//        val spot = LocalSpot(
//            lat = 0.0F,
//            lng = 0.0F,
//            message = "Hello From Preview!",
//            imageUrl = "",
//            spotId = "",
//            reachedCount = 100,
//            createdAt = "2024-10-09T23:31:15+09:00",
//        )
//        val rankingTestList: ImmutableList<LocalSpot> =
//            List(10) {
//                spot
//            }.toImmutableList()
//
//        TravelingSpotScreen(
//            spotList = rankingTestList,
//            onTripCancelButtonClick = { },
//            onSendLocalSpot = {}
//        )
//    }
//}