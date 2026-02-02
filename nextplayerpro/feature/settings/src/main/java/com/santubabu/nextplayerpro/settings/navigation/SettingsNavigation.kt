package com.santubabu.nextplayerpro.settings.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.navOptions
import com.santubabu.nextplayerpro.settings.Setting
import com.santubabu.nextplayerpro.settings.SettingsScreen

const val settingsNavigationRoute = "settings_route"

fun NavController.navigateToSettings(navOptions: NavOptions? = navOptions { launchSingleTop = true }) {
    this.navigate(settingsNavigationRoute, navOptions)
}

fun NavGraphBuilder.settingsScreen(onNavigateUp: () -> Unit, onItemClick: (Setting) -> Unit) {
    composable(route = settingsNavigationRoute) {
        SettingsScreen(onNavigateUp = onNavigateUp, onItemClick = onItemClick)
    }
}
