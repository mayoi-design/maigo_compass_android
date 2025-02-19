package jp.ac.mayoi.maigocompass

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import jp.ac.mayoi.core.navigation.MemoryNavigation
import jp.ac.mayoi.core.navigation.OnboardingNavigation
import jp.ac.mayoi.core.navigation.RankingNavigation
import jp.ac.mayoi.core.resource.MaigoCompassTheme
import jp.ac.mayoi.core.resource.maigoNavigationBarItemColors
import jp.ac.mayoi.core.resource.textStyleCaption

@Composable
fun RootScreen(
    navController: NavHostController
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination?.hierarchy
    val isShowBottomBar = remember(navController.currentDestination) {
        listOf(
            OnboardingNavigation::class,
            RankingNavigation::class,
            MemoryNavigation::class,
        ).any { navController.currentDestination?.hasRoute(it) == true }
    }

    Scaffold(
        bottomBar = {
            if (isShowBottomBar) {
                NavigationBar {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceAround
                    ) {
                        NavigationBarItem(
                            selected = currentDestination?.any {
                                it.route == OnboardingNavigation::class.qualifiedName
                            } == true,
                            onClick = { navController.navigate(OnboardingNavigation) },
                            icon = {
                                Icon(
                                    painter = painterResource(R.drawable.navbaritem_travel),
                                    contentDescription = null,
                                    modifier = Modifier.size(40.dp),
                                )
                            },
                            label = {
                                Text(
                                    text = "旅をする",
                                    style = textStyleCaption,
                                )
                            },
                            colors = maigoNavigationBarItemColors,
                        )
                        NavigationBarItem(
                            selected = currentDestination?.any {
                                it.route == RankingNavigation::class.qualifiedName
                            } == true,
                            onClick = { navController.navigate(RankingNavigation) },
                            icon = {
                                Icon(
                                    painter = painterResource(R.drawable.navbaritem_ranking),
                                    contentDescription = null,
                                    modifier = Modifier.size(40.dp),
                                )
                            },
                            label = {
                                Text(
                                    text = "ランキング",
                                    style = textStyleCaption,
                                )
                            },
                            colors = maigoNavigationBarItemColors,
                        )
                        NavigationBarItem(
                            selected = currentDestination?.any {
                                it.route == MemoryNavigation::class.qualifiedName
                            } == true,
                            onClick = { navController.navigate(MemoryNavigation) },
                            icon = {
                                Icon(
                                    painter = painterResource(R.drawable.navbaritem_memory),
                                    contentDescription = null,
                                    modifier = Modifier.size(40.dp),
                                )
                            },
                            label = {
                                Text(
                                    text = "おもいで",
                                    style = textStyleCaption,
                                )
                            },
                            colors = maigoNavigationBarItemColors,
                        )
                    }
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
private fun BottomNavigationPreview() {
    MaigoCompassTheme {
        RootScreen(navController = rememberNavController())
    }
}