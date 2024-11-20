package jp.ac.mayoi.maigocompass.presentation

import android.location.Location
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
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
    navController: NavHostController
) {
    NavHost(
        navController = navController,
        startDestination = WatchWaitNavigation,
    ) {
        composable<TripNavigation> {
            val travelingViewModel: TravelingViewModel = koinViewModel {
                // 本当はここをNavArgsで持ってくる
                // 1. Waitingでスマホから目的地情報を受け取る
                // 2. 目的地情報を使って遷移
                // 3. ここで目的地情報をViewModelに詰めてViewを作成
                // の流れ
                val destination = run {
                    Location(null).also {
                        // とりあえず五稜郭にしてある
                        it.latitude = 41.797653393691476
                        it.longitude = 140.7550099479477
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
                }
            )
        }
        composable<SettingsNavigation> {
        }
    }
}