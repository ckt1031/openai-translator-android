package com.ckt1031.openai.translator.items

import androidx.annotation.DrawableRes
import com.ckt1031.openai.translator.R

sealed class Screen(val route: String, @DrawableRes val resourceId: Int, val title: String) {
    object Translate : Screen("Translate", R.drawable.baseline_translate_24, "Translate")
    //object History : Screen("History", R.drawable.baseline_history_24, "History")
    object Settings : Screen("Settings", R.drawable.baseline_settings_24, "Settings")
}

public val navigationItems = listOf(
    Screen.Translate,
    //Screen.History,
    Screen.Settings
)