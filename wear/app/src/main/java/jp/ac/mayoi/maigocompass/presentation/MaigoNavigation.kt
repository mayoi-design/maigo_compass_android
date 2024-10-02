package jp.ac.mayoi.maigocompass.presentation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import jp.ac.mayoi.wear.core.navigation.Pairing
import jp.ac.mayoi.wear.core.navigation.Settings
import jp.ac.mayoi.wear.core.navigation.Trip

@Composable
fun WearNavigation(
    navController: NavHostController
) {

    NavHost(
        navController = navController,
        startDestination = Pairing
    ) {
        composable<Trip> {

        }

        composable<Pairing> {

        }

        composable<Settings> {

        }
    }
}