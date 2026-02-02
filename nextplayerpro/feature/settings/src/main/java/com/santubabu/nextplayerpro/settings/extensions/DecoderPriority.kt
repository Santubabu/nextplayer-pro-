package com.santubabu.nextplayerpro.settings.extensions

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.santubabu.nextplayerpro.core.model.DecoderPriority
import com.santubabu.nextplayerpro.core.ui.R

@Composable
fun DecoderPriority.name(): String {
    val stringRes = when (this) {
        DecoderPriority.PREFER_DEVICE -> R.string.prefer_device_decoders
        DecoderPriority.PREFER_APP -> R.string.prefer_app_decoders
        DecoderPriority.DEVICE_ONLY -> R.string.device_decoders_only
    }

    return stringResource(id = stringRes)
}
