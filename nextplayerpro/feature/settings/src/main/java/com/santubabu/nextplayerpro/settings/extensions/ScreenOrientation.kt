package com.santubabu.nextplayerpro.settings.extensions

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.santubabu.nextplayerpro.core.model.ScreenOrientation
import com.santubabu.nextplayerpro.core.ui.R

@Composable
fun ScreenOrientation.name(): String {
    val stringRes = when (this) {
        ScreenOrientation.AUTOMATIC -> R.string.automatic
        ScreenOrientation.LANDSCAPE -> R.string.landscape
        ScreenOrientation.LANDSCAPE_REVERSE -> R.string.landscape_reverse
        ScreenOrientation.LANDSCAPE_AUTO -> R.string.landscape_auto
        ScreenOrientation.PORTRAIT -> R.string.portrait
        ScreenOrientation.VIDEO_ORIENTATION -> R.string.video_orientation
    }

    return stringResource(id = stringRes)
}
