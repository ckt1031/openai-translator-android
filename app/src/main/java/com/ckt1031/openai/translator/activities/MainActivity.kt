package com.ckt1031.openai.translator.activities

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.core.view.WindowCompat
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.ckt1031.openai.translator.items.Screen
import com.ckt1031.openai.translator.ui.components.BottomNavigationBar
import com.ckt1031.openai.translator.ui.components.TopBar
import com.ckt1031.openai.translator.ui.screens.HistoryScreen
import com.ckt1031.openai.translator.ui.screens.SettingsScreen
import com.ckt1031.openai.translator.ui.screens.TranslateScreen

class MainActivity : ComponentActivity() {
    val Context.dataStore by preferencesDataStore(name = "settings")

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        window.setFlags(
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        )

        WindowCompat.setDecorFitsSystemWindows(window, false)


        setContent {
            View(dataStore)
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun View(dataStore: DataStore<Preferences>) {
    val navController = rememberNavController()
    val currentScreen = remember { mutableStateOf(Screen.Translate.route) }

    val animationState by remember {
        mutableStateOf(MutableTransitionState(false))
    }

    LaunchedEffect(Unit) {
        animationState.targetState = true
    }

    // Listen to navigation changes and update the currentScreen state
    LaunchedEffect(navController) {
        navController.currentBackStackEntryFlow.collect { backStackEntry ->
            animationState.targetState = true
            currentScreen.value = backStackEntry.destination.route ?: Screen.Translate.route
        }
    }

    Surface {
        Scaffold(
            bottomBar = { BottomNavigationBar(navController) },
            topBar = { TopBar(navController) },
            content = { padding ->
                Column(
                    modifier = Modifier.padding(padding).fillMaxSize()
                ) {
                    AnimatedVisibility(
                        visibleState = animationState,
                        enter = slideInHorizontally(
                            initialOffsetX = { 1000 }, // Slide in from the right
                            animationSpec = tween(700)
                        ) + fadeIn(animationSpec = tween(700)),
                        exit = slideOutHorizontally(
                            targetOffsetX = { -1000 }, // Slide out to the left
                            animationSpec = tween(700)
                        ) + fadeOut(animationSpec = tween(700))
                    ) {
                        NavHost(navController, startDestination = Screen.Translate.route) {
                            composable(Screen.Translate.route) { TranslateScreen(dataStore) }
//                            composable(Screen.History.route) { HistoryScreen() }
                            composable(Screen.Settings.route) { SettingsScreen(dataStore) }
                        }
                    }
                }
            }
        )
    }
}
