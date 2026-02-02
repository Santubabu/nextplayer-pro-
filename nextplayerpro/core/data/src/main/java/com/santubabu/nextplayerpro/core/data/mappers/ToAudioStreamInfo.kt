package com.santubabu.nextplayerpro.core.data.mappers

import com.santubabu.nextplayerpro.core.database.entities.AudioStreamInfoEntity
import com.santubabu.nextplayerpro.core.model.AudioStreamInfo

fun AudioStreamInfoEntity.toAudioStreamInfo() = AudioStreamInfo(
    index = index,
    title = title,
    codecName = codecName,
    language = language,
    disposition = disposition,
    bitRate = bitRate,
    sampleFormat = sampleFormat,
    sampleRate = sampleRate,
    channels = channels,
    channelLayout = channelLayout,
)
