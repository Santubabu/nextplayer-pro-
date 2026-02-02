package com.santubabu.nextplayerpro.core.database.relations

import androidx.room.Embedded
import androidx.room.Relation
import com.santubabu.nextplayerpro.core.database.entities.DirectoryEntity
import com.santubabu.nextplayerpro.core.database.entities.MediumEntity

data class DirectoryWithMedia(
    @Embedded val directory: DirectoryEntity,
    @Relation(
        entity = MediumEntity::class,
        parentColumn = "path",
        entityColumn = "parent_path",
    )
    val media: List<MediumWithInfo>,
)
