package com.santubabu.nextplayerpro.core.data.mappers

import com.santubabu.nextplayerpro.core.database.entities.VideoStreamInfoEntity
import com.santubabu.nextplayerpro.core.model.VideoStreamInfo

fun VideoStreamInfoEntity.toVideoStreamInfo() = VideoStreamInfo(
    index = index,
    title = title,
    codecName = codecName,
    language = language,
    disposition = disposition,
    bitRate = bitRate,
    frameRate = frameRate,
    frameWidth = frameWidth,
    frameHeight = frameHeight,
)
