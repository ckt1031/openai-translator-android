package com.ckt1031.openai.translator.ui.components

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.ckt1031.openai.translator.items.navigationItems

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(navController: NavController) {
    val currentDestination = navController.currentBackStackEntryAsState().value?.destination
    val currentScreen = navigationItems.find { it.route == currentDestination?.route }
    TopAppBar(
        title = { Text(text = currentScreen?.title ?: "") }
    )
}