package jp.ac.mayoi.maigocompass

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import jp.ac.mayoi.core.navigation.ImpressionsNavigation
import jp.ac.mayoi.core.navigation.OnboardingNavigation
import jp.ac.mayoi.core.navigation.RankingNavigation
import jp.ac.mayoi.core.navigation.ShareNavigation
import jp.ac.mayoi.core.navigation.TravelingNavigation

@Composable
fun PhoneNavHost(
    navController: NavHostController = rememberNavController(),
){
    NavHost(
        navController = navController,
        startDestination = OnboardingNavigation,
    ){
        composable<OnboardingNavigation>{

        }
        composable<RankingNavigation>{

        }
        composable<ImpressionsNavigation>{

        }
        composable<ShareNavigation>{

        }
        composable<TravelingNavigation>{

        }
    }
}