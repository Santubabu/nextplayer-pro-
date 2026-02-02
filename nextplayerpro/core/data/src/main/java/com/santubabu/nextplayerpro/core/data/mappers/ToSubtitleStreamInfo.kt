package com.santubabu.nextplayerpro.core.data.mappers

import com.santubabu.nextplayerpro.core.database.entities.SubtitleStreamInfoEntity
import com.santubabu.nextplayerpro.core.model.SubtitleStreamInfo

fun SubtitleStreamInfoEntity.toSubtitleStreamInfo() = SubtitleStreamInfo(
    index = index,
    title = title,
    codecName = codecName,
    language = language,
    disposition = disposition,
)
