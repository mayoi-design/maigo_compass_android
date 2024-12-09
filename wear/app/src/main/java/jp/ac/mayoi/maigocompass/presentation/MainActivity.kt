/* While this template provides a good starting point for using Wear Compose, you can always
 * take a look at https://github.com/android/wear-os-samples/tree/main/ComposeStarter and
 * https://github.com/android/wear-os-samples/tree/main/ComposeAdvanced to find the most up to date
 * changes to the libraries and their usages.
 */

package jp.ac.mayoi.maigocompass.presentation

import android.location.Location
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.compose.rememberNavController
import jp.ac.mayoi.wear.features.traveling.TravelingViewModel
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

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()

        super.onCreate(savedInstanceState)

        setContent {
            KoinContext {
                WearNavigation(
                    navController = rememberNavController(),
                    travelingViewModel = travelingViewModel
                )
            }
        }
    }
}
