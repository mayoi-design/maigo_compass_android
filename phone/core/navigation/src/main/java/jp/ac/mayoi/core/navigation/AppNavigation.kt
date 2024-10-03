package jp.ac.mayoi.core.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import kotlinx.serialization.Serializable

//インプレッションを見る
@Serializable
object Impressions

//オンボーディング
@Serializable
object Onboarding

//ランキング
@Serializable
object Ranking

//シェア
@Serializable
object Share

//歩いてる時
@Serializable
object Traveling

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
        composable<Impressions>{

        }
        composable<Share>{

        }
        composable<Traveling>{

        }
    }
}
