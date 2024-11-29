/* While this template provides a good starting point for using Wear Compose, you can always
 * take a look at https://github.com/android/wear-os-samples/tree/main/ComposeStarter and
 * https://github.com/android/wear-os-samples/tree/main/ComposeAdvanced to find the most up to date
 * changes to the libraries and their usages.
 */

package jp.ac.mayoi.maigocompass.presentation

import android.location.Location
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.compose.rememberNavController
import com.google.android.gms.wearable.MessageClient
import com.google.android.gms.wearable.MessageEvent
import com.google.android.gms.wearable.Wearable
import jp.ac.mayoi.common.model.RemoteSpotShrinkList
import jp.ac.mayoi.wear.features.traveling.TravelingViewModel
import jp.ac.mayoi.wear.repository.implementations.TravelingRepositoryImpl.Companion.LOCATION_DATA
import kotlinx.serialization.json.Json
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.compose.KoinContext
import org.koin.core.parameter.parametersOf

class MainActivity : ComponentActivity() {

    private val travelingViewModel: TravelingViewModel by viewModel(
        parameters = {
            val location = Location(null).apply {
                latitude = 41.796963164934354
                longitude = 140.7567424820125
            }
            parametersOf(location)
        }
    )

    private val messageClient by lazy { Wearable.getMessageClient(this) }
    private val listner = object : MessageClient.OnMessageReceivedListener {
        override fun onMessageReceived(p0: MessageEvent) {
            Log.d("Message", "Message Received")
            when (p0.path) {
                LOCATION_DATA -> {
                    val deserialized =
                        Json.decodeFromString<RemoteSpotShrinkList>(p0.data.toString(Charsets.UTF_8))
                    travelingViewModel.updateRecommendSpot(deserialized)
                }
            }
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()

        super.onCreate(savedInstanceState)

        setContent {
            KoinContext {
                // val TravelingViewModel = koinViewModel<TravelingViewModel>()
                WearNavigation(
                    navController = rememberNavController(),
                    travelingViewModel = travelingViewModel
                )
            }
        }
    }

    override fun onResume() {
        super.onResume()
        messageClient.addListener(listner)
    }

    override fun onPause() {
        super.onPause()
        messageClient.removeListener(listner)
    }
}
