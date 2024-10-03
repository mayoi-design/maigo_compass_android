package jp.ac.mayoi.core.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import kotlinx.serialization.Serializable

//目的地選択
@Serializable
object SelectDestination

//歩いてる時
@Serializable
object Onboarding

//ランキング
@Serializable
object Ranking

//おもいで
@Serializable
object MyMemory

@Composable
fun PhoneNavHost(
    navController: NavHostController = rememberNavController(),
){
    NavHost(
        navController = navController,
        startDestination = Onboarding
    ){
        composable<Onboarding>{

        }
        composable<Ranking>{

        }
        composable<SelectDestination>{

        }
        composable<MyMemory>{

        }
    }
}
