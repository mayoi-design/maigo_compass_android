package jp.ac.mayoi.maigocompass

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import jp.ac.mayoi.core.resource.colorAccent
import jp.ac.mayoi.core.resource.colorTextCaption
import jp.ac.mayoi.maigocompass.bottomNavigation.BottomNavItem
import jp.ac.mayoi.maigocompass.bottomNavigation.BottomNavigationItemList

@Composable
fun RootScreen(){
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    Scaffold(
        bottomBar = {
            NavigationBar {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceAround
                ) {
                    BottomNavigationItemList.entries.forEach() { item ->
                        val selected = currentDestination == item.route
                        BottomNavItem(
                            icon = item.icon,
                            label = item.label,
                            onClick = { navController.navigate(item.route) },
                            color = if (selected) {
                                colorAccent
                            } else {
                                colorTextCaption
                            }
                        )
                    }
                }
            }
        }
    ) { innerPadding ->
        PhoneNavHost(
            navController = navController,
            modifier = Modifier.padding(innerPadding),
        )
    }
}

@Preview
@Composable
fun BottomNavigationPreview(){
    RootScreen()
}