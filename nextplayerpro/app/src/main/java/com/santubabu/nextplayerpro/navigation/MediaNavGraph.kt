package com.santubabu.nextplayerpro.navigation

import android.content.Context
import android.content.Intent
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.navigation
import com.santubabu.nextplayerpro.feature.player.PlayerActivity
import com.santubabu.nextplayerpro.feature.player.utils.PlayerApi
import com.santubabu.nextplayerpro.feature.videopicker.navigation.MediaPickerRoute
import com.santubabu.nextplayerpro.feature.videopicker.navigation.mediaPickerScreen
import com.santubabu.nextplayerpro.feature.videopicker.navigation.navigateToMediaPickerScreen
import com.santubabu.nextplayerpro.settings.navigation.navigateToSettings
import kotlinx.serialization.Serializable

@Serializable
data object MediaRootRoute

fun NavGraphBuilder.mediaNavGraph(
    context: Context,
    navController: NavHostController,
) {
    navigation<MediaRootRoute>(startDestination = MediaPickerRoute()) {
        mediaPickerScreen(
            onNavigateUp = navController::navigateUp,
            onPlayVideo = { uri ->
                val intent = Intent(context, PlayerActivity::class.java).apply {
                    action = Intent.ACTION_VIEW
                    data = uri
                }
                context.startActivity(intent)
            },
            onPlayVideos = { uris ->
                val intent = Intent(context, PlayerActivity::class.java).apply {
                    action = Intent.ACTION_VIEW
                    data = uris.first()
                    putParcelableArrayListExtra(PlayerApi.API_PLAYLIST, ArrayList(uris))
                }
                context.startActivity(intent)
            },
            onFolderClick = navController::navigateToMediaPickerScreen,
            onSettingsClick = navController::navigateToSettings,
        )
    }
}
