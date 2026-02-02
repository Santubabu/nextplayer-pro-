package com.santubabu.nextplayerpro.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.navigation
import com.santubabu.nextplayerpro.settings.Setting
import com.santubabu.nextplayerpro.settings.navigation.aboutPreferencesScreen
import com.santubabu.nextplayerpro.settings.navigation.appearancePreferencesScreen
import com.santubabu.nextplayerpro.settings.navigation.audioPreferencesScreen
import com.santubabu.nextplayerpro.settings.navigation.decoderPreferencesScreen
import com.santubabu.nextplayerpro.settings.navigation.folderPreferencesScreen
import com.santubabu.nextplayerpro.settings.navigation.generalPreferencesScreen
import com.santubabu.nextplayerpro.settings.navigation.librariesScreen
import com.santubabu.nextplayerpro.settings.navigation.mediaLibraryPreferencesScreen
import com.santubabu.nextplayerpro.settings.navigation.navigateToAboutPreferences
import com.santubabu.nextplayerpro.settings.navigation.navigateToAppearancePreferences
import com.santubabu.nextplayerpro.settings.navigation.navigateToAudioPreferences
import com.santubabu.nextplayerpro.settings.navigation.navigateToDecoderPreferences
import com.santubabu.nextplayerpro.settings.navigation.navigateToFolderPreferencesScreen
import com.santubabu.nextplayerpro.settings.navigation.navigateToGeneralPreferences
import com.santubabu.nextplayerpro.settings.navigation.navigateToLibraries
import com.santubabu.nextplayerpro.settings.navigation.navigateToMediaLibraryPreferencesScreen
import com.santubabu.nextplayerpro.settings.navigation.navigateToPlayerPreferences
import com.santubabu.nextplayerpro.settings.navigation.navigateToSubtitlePreferences
import com.santubabu.nextplayerpro.settings.navigation.playerPreferencesScreen
import com.santubabu.nextplayerpro.settings.navigation.settingsNavigationRoute
import com.santubabu.nextplayerpro.settings.navigation.settingsScreen
import com.santubabu.nextplayerpro.settings.navigation.subtitlePreferencesScreen

const val SETTINGS_ROUTE = "settings_nav_route"

fun NavGraphBuilder.settingsNavGraph(
    navController: NavHostController,
) {
    navigation(
        startDestination = settingsNavigationRoute,
        route = SETTINGS_ROUTE,
    ) {
        settingsScreen(
            onNavigateUp = navController::navigateUp,
            onItemClick = { setting ->
                when (setting) {
                    Setting.APPEARANCE -> navController.navigateToAppearancePreferences()
                    Setting.MEDIA_LIBRARY -> navController.navigateToMediaLibraryPreferencesScreen()
                    Setting.PLAYER -> navController.navigateToPlayerPreferences()
                    Setting.DECODER -> navController.navigateToDecoderPreferences()
                    Setting.AUDIO -> navController.navigateToAudioPreferences()
                    Setting.SUBTITLE -> navController.navigateToSubtitlePreferences()
                    Setting.GENERAL -> navController.navigateToGeneralPreferences()
                    Setting.ABOUT -> navController.navigateToAboutPreferences()
                }
            },
        )
        appearancePreferencesScreen(
            onNavigateUp = navController::navigateUp,
        )
        mediaLibraryPreferencesScreen(
            onNavigateUp = navController::navigateUp,
            onFolderSettingClick = navController::navigateToFolderPreferencesScreen,
        )
        folderPreferencesScreen(
            onNavigateUp = navController::navigateUp,
        )
        playerPreferencesScreen(
            onNavigateUp = navController::navigateUp,
        )
        decoderPreferencesScreen(
            onNavigateUp = navController::navigateUp,
        )
        audioPreferencesScreen(
            onNavigateUp = navController::navigateUp,
        )
        subtitlePreferencesScreen(
            onNavigateUp = navController::navigateUp,
        )
        generalPreferencesScreen(
            onNavigateUp = navController::navigateUp,
        )
        aboutPreferencesScreen(
            onLibrariesClick = navController::navigateToLibraries,
            onNavigateUp = navController::navigateUp,
        )
        librariesScreen(
            onNavigateUp = navController::navigateUp,
        )
    }
}
