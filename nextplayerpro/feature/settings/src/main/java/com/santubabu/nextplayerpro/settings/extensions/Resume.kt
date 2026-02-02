package com.santubabu.nextplayerpro.settings.extensions

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.santubabu.nextplayerpro.core.model.Resume
import com.santubabu.nextplayerpro.core.ui.R

@Composable
fun Resume.name(): String {
    val stringRes = when (this) {
        Resume.YES -> R.string.yes
        Resume.NO -> R.string.no
    }

    return stringResource(id = stringRes)
}
