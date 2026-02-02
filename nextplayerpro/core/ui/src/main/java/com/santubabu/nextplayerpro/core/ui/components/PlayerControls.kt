package com.santubabu.nextplayerpro.core.ui.composables

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.santubabu.nextplayerpro.core.ui.components.*

@Composable
fun NowPlayingScreen() {

    var progress by remember { mutableStateOf(0.3f) }
    var subtitleDelay by remember { mutableStateOf(0L) }

    PlayerContainer {

        Spacer(modifier = Modifier.height(16.dp))

        PlayerSeekBar(
            value = progress,
            onValueChange = { progress = it }
        )

        Spacer(modifier = Modifier.height(12.dp))

        PlayerMainButton(
            icon = Icons.Default.PlayArrow,
            onClick = { /* play / pause */ }
        )

        Spacer(modifier = Modifier.height(24.dp))

        SubtitleDelayControl(
            delayMs = subtitleDelay,
            onDelayChange = { subtitleDelay = it }
        )

        Spacer(modifier = Modifier.height(16.dp))
    }
}