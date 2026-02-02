package com.santubabu.nextplayerpro.core.ui.composables

import android.content.Context
import android.graphics.Color as AndroidColor
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.google.android.exoplayer2.*
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector
import com.google.android.exoplayer2.ui.CaptionStyleCompat
import com.google.android.exoplayer2.ui.StyledPlayerView
import com.santubabu.nextplayerpro.core.ui.components.*
import kotlin.math.roundToInt

@Composable
fun NowPlayingScreen() {

    val context = LocalContext.current
    val prefs = remember { context.getSharedPreferences("subs", Context.MODE_PRIVATE) }

    val trackSelector = remember { DefaultTrackSelector(context) }
    val player = remember {
        ExoPlayer.Builder(context)
            .setTrackSelector(trackSelector)
            .build()
    }

    // 🔹 STATE (RESTORED)
    var subtitleDelay by remember { mutableStateOf(prefs.getLong("delay", 0L)) }
    var subtitleSize by remember { mutableStateOf(prefs.getFloat("size", 16f)) }
    var subtitleYOffset by remember { mutableStateOf(prefs.getInt("yOffset", 0)) }
    var outlineWidth by remember { mutableStateOf(prefs.getFloat("outline", 2f)) }
    var subtitlesEnabled by remember { mutableStateOf(prefs.getBoolean("enabled", true)) }

    // 🔹 APPLY SUBTITLE DELAY
    LaunchedEffect(subtitleDelay) {
        prefs.edit().putLong("delay", subtitleDelay).apply()
        trackSelector.setParameters(
            trackSelector.parameters
                .buildUpon()
                .setSubtitleDelayMs(subtitleDelay)
                .build()
        )
    }

    // 🔹 SUBTITLE ON / OFF
    LaunchedEffect(subtitlesEnabled) {
        prefs.edit().putBoolean("enabled", subtitlesEnabled).apply()
        trackSelector.setParameters(
            trackSelector.parameters
                .buildUpon()
                .setTrackTypeDisabled(C.TRACK_TYPE_TEXT, !subtitlesEnabled)
                .build()
        )
    }

    DisposableEffect(Unit) {
        onDispose { player.release() }
    }

    Column {

        // 🎬 VIDEO VIEW (GESTURE SYNC + POSITION + OUTLINE)
        AndroidView(
            modifier = Modifier
                .fillMaxWidth()
                .height(220.dp)
                .pointerInput(Unit) {
                    detectHorizontalDragGestures { _, dragAmount ->
                        subtitleDelay += (dragAmount / 10).roundToInt() * 100L
                    }
                },
            factory = {
                StyledPlayerView(it).apply {
                    this.player = player
                    useController = false
                }
            },
            update = { view ->
                view.subtitleView?.apply {
                    setFixedTextSize(Cue.TEXT_SIZE_TYPE_SP, subtitleSize)
                    setPadding(0, 0, 0, subtitleYOffset)
                    setStyle(
                        CaptionStyleCompat(
                            AndroidColor.WHITE,
                            AndroidColor.TRANSPARENT,
                            AndroidColor.TRANSPARENT,
                            CaptionStyleCompat.EDGE_TYPE_OUTLINE,
                            AndroidColor.BLACK,
                            null
                        ).also {
                            this.setFractionalTextSize(subtitleSize / 100)
                        }
                    )
                    setBottomPaddingFraction(outlineWidth / 100)
                }
            }
        )

        PlayerContainer {

            Spacer(modifier = Modifier.height(12.dp))

            PlayerMainButton(
                icon = Icons.Default.PlayArrow,
                onClick = {
                    if (player.isPlaying) player.pause() else player.play()
                }
            )

            Spacer(modifier = Modifier.height(12.dp))

            // 🔹 SUBTITLE TOGGLE
            Row(horizontalArrangement = Arrangement.Center, modifier = Modifier.fillMaxWidth()) {
                Text("Subtitles")
                Spacer(Modifier.width(8.dp))
                Switch(subtitlesEnabled, { subtitlesEnabled = it })
            }

            Spacer(modifier = Modifier.height(12.dp))

            // 🔹 DELAY CONTROL
            SubtitleDelayControl(
                delayMs = subtitleDelay,
                onDelayChange = { subtitleDelay = it }
            )

            Spacer(modifier = Modifier.height(12.dp))

            // 🔹 FONT SIZE
            Text("Subtitle Size")
            Slider(
                value = subtitleSize,
                onValueChange = {
                    subtitleSize = it
                    prefs.edit().putFloat("size", it).apply()
                },
                valueRange = 12f..32f
            )

            // 🔹 POSITION UP / DOWN
            Text("Subtitle Position")
            Slider(
                value = subtitleYOffset.toFloat(),
                onValueChange = {
                    subtitleYOffset = it.roundToInt()
                    prefs.edit().putInt("yOffset", subtitleYOffset).apply()
                },
                valueRange = 0f..300f
            )

            // 🔹 OUTLINE / SHADOW THICKNESS
            Text("Outline Thickness")
            Slider(
                value = outlineWidth,
                onValueChange = {
                    outlineWidth = it
                    prefs.edit().putFloat("outline", it).apply()
                },
                valueRange = 1f..6f
            )

            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}