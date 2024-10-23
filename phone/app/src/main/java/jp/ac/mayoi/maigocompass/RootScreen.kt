package jp.ac.mayoi.maigocompass

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import jp.ac.mayoi.core.navigation.MemoryNavigation
import jp.ac.mayoi.core.navigation.OnboardingNavigation
import jp.ac.mayoi.core.navigation.RankingNavigation
import jp.ac.mayoi.core.resource.MaigoCompassTheme
import jp.ac.mayoi.maigocompass.bottomNavigation.BottomNavItem

@Composable
fun RootScreen(){
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination?.hierarchy

    Scaffold(
        bottomBar = {
            NavigationBar {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceAround
                ) {
                    BottomNavItem(
                        label = "旅をする",
                        resId = R.drawable.navbaritem_travel,
                        isSelected = currentDestination?.any{
                            it.route == OnboardingNavigation::class.qualifiedName
                        } == true,
                        onClick = { navController.navigate(OnboardingNavigation)}
                    )
                    BottomNavItem(
                        label = "ランキング",
                        resId = R.drawable.navbaritem_ranking,
                        isSelected = currentDestination?.any{
                            it.route == RankingNavigation::class.qualifiedName
                        } == true,
                        onClick = {navController.navigate(RankingNavigation)}
                    )
                    BottomNavItem(
                        label = "おもいで",
                        resId = R.drawable.navbaritem_memory,
                        isSelected = currentDestination?.any{
                            it.route == MemoryNavigation::class.qualifiedName
                        } == true,
                        onClick = {navController.navigate(MemoryNavigation)}
                    )
                }
            }
        }
    ) { innerPadding ->
        PhoneNavHost(
            navController = navController,
            innerPadding = innerPadding,
        )
    }
}

@Preview
@Composable
private fun BottomNavigationPreview(){
    MaigoCompassTheme {
        RootScreen()
    }
}