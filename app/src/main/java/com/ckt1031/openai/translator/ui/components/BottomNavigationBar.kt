package com.ckt1031.openai.translator.ui.components

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.ckt1031.openai.translator.items.navigationItems
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.painterResource

@Composable
fun BottomNavigationBar(navController: NavController) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

//    BottomNavigation {
//        val currentDestination = navController.currentBackStackEntryAsState().value?.destination
//        navigationItems.forEach { screen ->
//            BottomNavigationItem(
//                icon = { Icon(painterResource(id = screen.resourceId), contentDescription = null) },
//                label = { Text(screen.title) },
//                selected = currentDestination?.route == screen.route,
//                onClick = {
//                    navController.navigate(screen.route) {
//                        // Avoid multiple copies of the same destination when reselecting the same item
//                        popUpTo(navController.graph.startDestinationId)
//                        launchSingleTop = true
//                    }
//                }
//            )
//        }
//    }

    NavigationBar {
        navigationItems.forEachIndexed { _, item ->
            NavigationBarItem(
                icon = { Icon(painterResource(item.resourceId), contentDescription = item.title) },
                label = { Text(item.title) },
                selected = currentRoute == item.title,
                onClick = {
                    navController.navigate(item.title) {
                        restoreState = true
                        launchSingleTop = true
                        navController.graph.startDestinationRoute?.let { screen_route ->
                            popUpTo(screen_route) {
                                saveState = true
                            }
                        }
                    }
                },
                alwaysShowLabel = false
            )
        }
    }
}
