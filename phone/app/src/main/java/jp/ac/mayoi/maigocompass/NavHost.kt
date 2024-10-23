package jp.ac.mayoi.maigocompass

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
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
    innerPadding:PaddingValues,
    navController: NavHostController = rememberNavController(),
){
    NavHost(
        navController = navController,
        startDestination = OnboardingNavigation,
        modifier = Modifier.padding(innerPadding)
    ){
        composable<OnboardingNavigation>{
            Box(modifier = Modifier.fillMaxSize()){
                Text("OnboardingScreen")
            }
        }
        composable<RankingNavigation>{
            Box(modifier = Modifier.fillMaxSize()){
                Text("RankingScreen")
            }
        }
        composable<MemoryNavigation>{
            Box(modifier = Modifier.fillMaxSize()){
                Text("MemoryScreen")
            }
        }
        composable<ShareNavigation>{

        }
        composable<TravelingNavigation>{

        }
    }
}