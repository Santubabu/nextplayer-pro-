package dev.anilbeesetti.nextplayer.feature.player.ui

import android.graphics.Typeface
import android.util.TypedValue
import android.view.accessibility.CaptioningManager
import androidx.annotation.OptIn
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat.getSystemService
import androidx.media3.common.Player
import androidx.media3.common.util.UnstableApi
import androidx.media3.ui.CaptionStyleCompat
import androidx.media3.ui.SubtitleView
import dev.anilbeesetti.nextplayer.core.model.Font
import dev.anilbeesetti.nextplayer.feature.player.extensions.toTypeface
import dev.anilbeesetti.nextplayer.feature.player.state.rememberCuesState
import kotlinx.coroutines.delay

@OptIn(UnstableApi::class)
@Composable
fun SubtitleView(
    modifier: Modifier = Modifier,
    player: Player,
    isInPictureInPictureMode: Boolean,
    configuration: SubtitleConfiguration,
    subtitleDelayMs: Long = 0L,                // ← NEW: delay in ms (positive = subs later)
    onDelayChanged: (Long) -> Unit = {},       // ← NEW: optional callback when user changes it
) {
    val cuesState = rememberCuesState(player)

    // Remember last applied delay to detect changes
    var lastAppliedDelay by remember { mutableLongStateOf(0L) }

    // Trigger resync only when delay actually changes
    LaunchedEffect(subtitleDelayMs) {
        if (subtitleDelayMs != lastAppliedDelay && player.isPlaying) {
            val currentPos = player.currentPosition
            // Tiny seek trick to force subtitle renderer resync
            player.seekTo(currentPos + 1)
            delay(16) // small wait to let seek start
            player.seekTo(currentPos)

            lastAppliedDelay = subtitleDelayMs
            onDelayChanged(subtitleDelayMs) // notify parent (save per video?)
        }
    }

    AndroidView(
        modifier = modifier.fillMaxSize(),
        factory = { context ->
            SubtitleView(context).apply {
                val captioningManager = getSystemService(context, CaptioningManager::class.java) ?: return@apply
                if (configuration.useSystemCaptionStyle) {
                    val systemCaptionStyle = CaptionStyleCompat.createFromCaptionStyle(captioningManager.userStyle)
                    setStyle(systemCaptionStyle)
                } else {
                    val userStyle = CaptionStyleCompat(
                        android.graphics.Color.WHITE,
                        android.graphics.Color.BLACK.takeIf { configuration.showBackground } ?: android.graphics.Color.TRANSPARENT,
                        android.graphics.Color.TRANSPARENT,
                        CaptionStyleCompat.EDGE_TYPE_DROP_SHADOW,
                        android.graphics.Color.BLACK,
                        Typeface.create(
                            configuration.font.toTypeface(),
                            Typeface.BOLD.takeIf { configuration.textBold } ?: Typeface.NORMAL,
                        ),
                    )
                    setStyle(userStyle)
                    setFixedTextSize(TypedValue.COMPLEX_UNIT_SP, configuration.textSize.toFloat())
                }
                setApplyEmbeddedStyles(configuration.applyEmbeddedStyles)
            }
        },
        update = { subtitleView ->
            subtitleView.setCues(cuesState.cues)
            if (isInPictureInPictureMode) {
                subtitleView.setFractionalTextSize(SubtitleView.DEFAULT_TEXT_SIZE_FRACTION)
            } else {
                subtitleView.setFixedTextSize(TypedValue.COMPLEX_UNIT_SP, configuration.textSize.toFloat())
            }
        },
    )
}

@Stable
data class SubtitleConfiguration(
    val useSystemCaptionStyle: Boolean,
    val showBackground: Boolean,
    val font: Font,
    val textSize: Int,
    val textBold: Boolean,
    val applyEmbeddedStyles: Boolean,
)
