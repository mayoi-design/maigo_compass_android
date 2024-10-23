package jp.ac.mayoi.maigocompass

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
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

@Composable
fun PhoneNavHost(
    navController: NavHostController = rememberNavController(),
    innerPadding:PaddingValues,
){
    NavHost(
        navController = navController,
        startDestination = OnboardingNavigation,
        modifier = Modifier.padding(innerPadding)
    ){
        composable<OnboardingNavigation>{

        }
        composable<RankingNavigation>{

        }
        composable<MemoryNavigation>{

        }
        composable<ShareNavigation>{

        }
        composable<TravelingNavigation>{

        }
    }
}