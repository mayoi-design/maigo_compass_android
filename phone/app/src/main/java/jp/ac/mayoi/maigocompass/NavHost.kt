package jp.ac.mayoi.maigocompass

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import jp.ac.mayoi.core.navigation.MemoryNavigation
import jp.ac.mayoi.core.navigation.OnboardingNavigation
import jp.ac.mayoi.core.navigation.RankingNavigation
import jp.ac.mayoi.core.navigation.ShareNavigation
import jp.ac.mayoi.core.navigation.TravelingNavigation
import jp.ac.mayoi.onboarding.OnboardingScreen
import jp.ac.mayoi.onboarding.OnboardingViewModel
import jp.ac.mayoi.ranking.RankingScreen
import jp.ac.mayoi.traveling.ParentScreen
import jp.ac.mayoi.traveling.TravelingViewModel
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

@Composable
fun PhoneNavHost(
    innerPadding: PaddingValues,
    navController: NavHostController = rememberNavController(),
) {
    NavHost(
        navController = navController,
        startDestination = OnboardingNavigation,
        modifier = Modifier.padding(innerPadding)
    ) {
        composable<OnboardingNavigation> {
            val onboardingViewModel: OnboardingViewModel = koinViewModel()
            val coroutineScope = rememberCoroutineScope()
            OnboardingScreen(
                onCameraPositionChanged = onboardingViewModel::onCameraChanged,
                onDecideClicked = {
                    coroutineScope.launch {
                        onboardingViewModel
                            .onBeforeNavigateToTravelingScreen()
                            .await()
                            .onSuccess {
                                navController.navigate(TravelingNavigation)
                            }
                    }
                },
                onCurrentPositionClicked = {},
            )
        }
        composable<RankingNavigation> {
            RankingScreen(
                viewModel = koinViewModel()
            )
        }
        composable<MemoryNavigation> {
            Box(modifier = Modifier.fillMaxSize()) {
                Text("MemoryScreen")
            }
        }
        composable<ShareNavigation> {

        }
        composable<TravelingNavigation> {
            val travelingViewModel: TravelingViewModel = koinViewModel()
            val coroutineScope = rememberCoroutineScope()
            ParentScreen(
                viewModel = travelingViewModel,
                onTripCancelButtonClick = {
                    coroutineScope.launch {
                        travelingViewModel
                            .notifyFinishTravel()
                            .await()
                            .onSuccess {
                                navController.popBackStack()
                            }
                    }
                },
                onRetryButtonClick = {
                    travelingViewModel.getNearSpot()
                }
            )
        }
    }
}