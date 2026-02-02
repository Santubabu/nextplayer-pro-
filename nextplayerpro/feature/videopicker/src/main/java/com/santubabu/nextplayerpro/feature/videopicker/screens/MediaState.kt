package com.santubabu.nextplayerpro.feature.videopicker.screens

import com.santubabu.nextplayerpro.core.model.Folder

sealed interface MediaState {
    data object Loading : MediaState
    data class Success(val data: Folder?) : MediaState
}
