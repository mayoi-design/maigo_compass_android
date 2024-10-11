package jp.ac.mayoi.maigocompass

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import jp.ac.mayoi.core.navigation.Screen

@Composable
fun PhoneNavHost(
    navController: NavHostController = rememberNavController(),
    innerPadding:PaddingValues,
){
    NavHost(
        navController = navController,
        startDestination = Screen.OnboardingNavigation,
        modifier = Modifier.padding(innerPadding)
    ){
        composable<Screen.OnboardingNavigation>{

        }
        composable<Screen.RankingNavigation>{

        }
        composable<Screen.MemoryNavigation>{

        }
        composable<Screen.ShareNavigation>{

        }
        composable<Screen.TravelingNavigation>{

        }
    }
}