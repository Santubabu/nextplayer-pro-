package com.santubabu.nextplayerpro.core.data.mappers

import com.santubabu.nextplayerpro.core.data.models.VideoState
import com.santubabu.nextplayerpro.core.database.converter.UriListConverter
import com.santubabu.nextplayerpro.core.database.entities.MediumStateEntity

fun MediumStateEntity.toVideoState(): VideoState {
    return VideoState(
        path = uriString,
        position = playbackPosition.takeIf { it != 0L },
        audioTrackIndex = audioTrackIndex,
        subtitleTrackIndex = subtitleTrackIndex,
        playbackSpeed = playbackSpeed,
        externalSubs = UriListConverter.fromStringToList(externalSubs),
        videoScale = videoScale,
    )
}
