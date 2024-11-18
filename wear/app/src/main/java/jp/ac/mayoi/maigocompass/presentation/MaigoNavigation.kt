package jp.ac.mayoi.maigocompass.presentation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import jp.ac.mayoi.wear.core.navigation.SettingsNavigation
import jp.ac.mayoi.wear.core.navigation.TripNavigation
import jp.ac.mayoi.wear.core.navigation.WatchWaitNavigation
import jp.ac.mayoi.wear.features.traveling.TravelingRoot
import jp.ac.mayoi.wear.features.waiting.WaitingSwipe
import org.koin.androidx.compose.koinViewModel


@Composable
fun WearNavigation(
    navController: NavHostController
) {
    NavHost(
        navController = navController,
        startDestination = WatchWaitNavigation,
    ) {
        composable<TripNavigation> {
            TravelingRoot(
                viewModel = koinViewModel()
            )
        }
        composable<WatchWaitNavigation> {
            WaitingSwipe(
                onSettingButtonClick = {
                    navController.navigate(
                        SettingsNavigation
                    )
                }
            )
        }
        composable<SettingsNavigation> {
        }
    }
}