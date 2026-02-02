package com.santubabu.nextplayerpro.core.data.repository

import android.net.Uri
import com.santubabu.nextplayerpro.core.data.mappers.toFolder
import com.santubabu.nextplayerpro.core.data.mappers.toVideo
import com.santubabu.nextplayerpro.core.data.mappers.toVideoState
import com.santubabu.nextplayerpro.core.data.models.VideoState
import com.santubabu.nextplayerpro.core.database.converter.UriListConverter
import com.santubabu.nextplayerpro.core.database.dao.DirectoryDao
import com.santubabu.nextplayerpro.core.database.dao.MediumDao
import com.santubabu.nextplayerpro.core.database.dao.MediumStateDao
import com.santubabu.nextplayerpro.core.database.entities.MediumStateEntity
import com.santubabu.nextplayerpro.core.database.relations.DirectoryWithMedia
import com.santubabu.nextplayerpro.core.database.relations.MediumWithInfo
import com.santubabu.nextplayerpro.core.model.Folder
import com.santubabu.nextplayerpro.core.model.Video
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class LocalMediaRepository @Inject constructor(
    private val mediumDao: MediumDao,
    private val mediumStateDao: MediumStateDao,
    private val directoryDao: DirectoryDao,
) : MediaRepository {

    override fun getVideosFlow(): Flow<List<Video>> {
        return mediumDao.getAllWithInfo().map { it.map(MediumWithInfo::toVideo) }
    }

    override fun getVideosFlowFromFolderPath(folderPath: String): Flow<List<Video>> {
        return mediumDao.getAllWithInfoFromDirectory(folderPath).map { it.map(MediumWithInfo::toVideo) }
    }

    override fun getFoldersFlow(): Flow<List<Folder>> {
        return directoryDao.getAllWithMedia().map { it.map(DirectoryWithMedia::toFolder) }
    }

    override suspend fun getVideoByUri(uri: String): Video? {
        return mediumDao.getWithInfo(uri)?.toVideo()
    }

    override suspend fun getVideoState(uri: String): VideoState? {
        return mediumStateDao.get(uri)?.toVideoState()
    }

    override suspend fun updateMediumLastPlayedTime(uri: String, lastPlayedTime: Long) {
        val stateEntity = mediumStateDao.get(uri) ?: MediumStateEntity(uriString = uri)

        mediumStateDao.upsert(
            mediumState = stateEntity.copy(
                lastPlayedTime = lastPlayedTime,
            ),
        )
    }

    override suspend fun updateMediumPosition(uri: String, position: Long) {
        val duration = mediumDao.get(uri)?.duration ?: position.plus(1)
        val adjustedPosition = position.takeIf { it < duration } ?: Long.MIN_VALUE.plus(1)

        val stateEntity = mediumStateDao.get(uri) ?: MediumStateEntity(uriString = uri)

        mediumStateDao.upsert(
            mediumState = stateEntity.copy(
                playbackPosition = adjustedPosition,
                lastPlayedTime = System.currentTimeMillis(),
            ),
        )
    }

    override suspend fun updateMediumPlaybackSpeed(uri: String, playbackSpeed: Float) {
        val stateEntity = mediumStateDao.get(uri) ?: MediumStateEntity(uriString = uri)

        mediumStateDao.upsert(
            mediumState = stateEntity.copy(
                playbackSpeed = playbackSpeed,
                lastPlayedTime = System.currentTimeMillis(),
            ),
        )
    }

    override suspend fun updateMediumAudioTrack(uri: String, audioTrackIndex: Int) {
        val stateEntity = mediumStateDao.get(uri) ?: MediumStateEntity(uriString = uri)

        mediumStateDao.upsert(
            mediumState = stateEntity.copy(
                audioTrackIndex = audioTrackIndex,
                lastPlayedTime = System.currentTimeMillis(),
            ),
        )
    }

    override suspend fun updateMediumSubtitleTrack(uri: String, subtitleTrackIndex: Int) {
        val stateEntity = mediumStateDao.get(uri) ?: MediumStateEntity(uriString = uri)

        mediumStateDao.upsert(
            mediumState = stateEntity.copy(
                subtitleTrackIndex = subtitleTrackIndex,
                lastPlayedTime = System.currentTimeMillis(),
            ),
        )
    }

    override suspend fun updateMediumZoom(uri: String, zoom: Float) {
        val stateEntity = mediumStateDao.get(uri) ?: MediumStateEntity(uriString = uri)

        mediumStateDao.upsert(
            mediumState = stateEntity.copy(
                videoScale = zoom,
                lastPlayedTime = System.currentTimeMillis(),
            ),
        )
    }

    override suspend fun addExternalSubtitleToMedium(uri: String, subtitleUri: Uri) {
        val stateEntity = mediumStateDao.get(uri) ?: MediumStateEntity(uriString = uri)
        val currentExternalSubs = UriListConverter.fromStringToList(stateEntity.externalSubs)

        if (currentExternalSubs.contains(subtitleUri)) return
        val newExternalSubs = UriListConverter.fromListToString(urlList = currentExternalSubs + subtitleUri)

        mediumStateDao.upsert(
            mediumState = stateEntity.copy(
                externalSubs = newExternalSubs,
                lastPlayedTime = System.currentTimeMillis(),
            ),
        )
    }
}
