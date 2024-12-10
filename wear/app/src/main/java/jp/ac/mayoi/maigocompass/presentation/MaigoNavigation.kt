package jp.ac.mayoi.maigocompass.presentation

import android.location.Location
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import jp.ac.mayoi.wear.core.navigation.SettingsNavigation
import jp.ac.mayoi.wear.core.navigation.TripNavigation
import jp.ac.mayoi.wear.core.navigation.WatchWaitNavigation
import jp.ac.mayoi.wear.features.traveling.TravelingRoot
import jp.ac.mayoi.wear.features.traveling.TravelingViewModel
import jp.ac.mayoi.wear.features.waiting.WaitingSwipe
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf


@Composable
fun WearNavigation(
    navController: NavHostController,
) {
    NavHost(
        navController = navController,
        startDestination = WatchWaitNavigation,
    ) {
        composable<TripNavigation> { backStackEntry ->
            val route: TripNavigation = backStackEntry.toRoute()
            val travelingViewModel: TravelingViewModel = koinViewModel {
                val destination = run {
                    Location(null).also {
                        it.latitude = route.destinationLat
                        it.longitude = route.destinationLng
                    }
                }
                parametersOf(destination)
            }
            TravelingRoot(
                viewModel = travelingViewModel,
            )
        }
        composable<WatchWaitNavigation> {
            WaitingSwipe(
                onSettingButtonClick = {
                    navController.navigate(
                        SettingsNavigation
                    )
                },
                onReceiveDestinationData = { lat, lng ->
                    val route = TripNavigation(
                        destinationLat = lat,
                        destinationLng = lng,
                    )

                    navController.navigate(route)
                }
            )
        }
        composable<SettingsNavigation> {
        }
    }
}