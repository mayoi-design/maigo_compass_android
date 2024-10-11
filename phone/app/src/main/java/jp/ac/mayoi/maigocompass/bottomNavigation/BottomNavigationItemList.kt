package jp.ac.mayoi.maigocompass.bottomNavigation

import jp.ac.mayoi.core.navigation.Screen
import jp.ac.mayoi.maigocompass.R

enum class BottomNavigationItemList(
    val label:String,
    val icon:Int,
    val route: Screen
){
    ONBOARDING(
        label = "旅をする",
        icon = R.drawable.navbaritem_travel,
        route = Screen.OnboardingNavigation
    ),
    RANKING(
        label = "ランキング",
        icon = R.drawable.navbaritem_ranking,
        route = Screen.RankingNavigation
    ),
    IMPRESSIONS(
        label = "おもいで",
        icon = R.drawable.navbaritem_impressions,
        route = Screen.ImpressionsNavigation
    )
}
