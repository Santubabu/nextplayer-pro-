package com.santubabu.nextplayerpro.settings.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.navOptions
import com.santubabu.nextplayerpro.settings.screens.audio.AudioPreferencesScreen

const val audioPreferencesNavigationRoute = "audio_preferences_route"

fun NavController.navigateToAudioPreferences(navOptions: NavOptions? = navOptions { launchSingleTop = true }) {
    this.navigate(audioPreferencesNavigationRoute, navOptions)
}

fun NavGraphBuilder.audioPreferencesScreen(onNavigateUp: () -> Unit) {
    composable(route = audioPreferencesNavigationRoute) {
        AudioPreferencesScreen(onNavigateUp = onNavigateUp)
    }
}
