package com.santubabu.nextplayerpro.core.database.relations

import androidx.room.Embedded
import androidx.room.Relation
import com.santubabu.nextplayerpro.core.database.entities.AudioStreamInfoEntity
import com.santubabu.nextplayerpro.core.database.entities.MediumEntity
import com.santubabu.nextplayerpro.core.database.entities.MediumStateEntity
import com.santubabu.nextplayerpro.core.database.entities.SubtitleStreamInfoEntity
import com.santubabu.nextplayerpro.core.database.entities.VideoStreamInfoEntity

data class MediumWithInfo(
    @Embedded val mediumEntity: MediumEntity,
    @Relation(
        parentColumn = "uri",
        entityColumn = "uri",
    )
    val mediumStateEntity: MediumStateEntity?,
    @Relation(
        parentColumn = "uri",
        entityColumn = "medium_uri",
    )
    val videoStreamInfo: VideoStreamInfoEntity?,
    @Relation(
        parentColumn = "uri",
        entityColumn = "medium_uri",
    )
    val audioStreamsInfo: List<AudioStreamInfoEntity>,
    @Relation(
        parentColumn = "uri",
        entityColumn = "medium_uri",
    )
    val subtitleStreamsInfo: List<SubtitleStreamInfoEntity>,
)
