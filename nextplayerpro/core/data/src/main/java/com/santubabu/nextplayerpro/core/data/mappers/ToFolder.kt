package com.santubabu.nextplayerpro.core.data.mappers

import com.santubabu.nextplayerpro.core.common.Utils
import com.santubabu.nextplayerpro.core.database.relations.DirectoryWithMedia
import com.santubabu.nextplayerpro.core.database.relations.MediumWithInfo
import com.santubabu.nextplayerpro.core.model.Folder

fun DirectoryWithMedia.toFolder() = Folder(
    name = directory.name,
    path = directory.path,
    dateModified = directory.modified,
    parentPath = directory.parentPath,
    formattedMediaSize = Utils.formatFileSize(media.sumOf { it.mediumEntity.size }),
    mediaList = media.map(MediumWithInfo::toVideo),
)
