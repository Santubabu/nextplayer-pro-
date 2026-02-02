package com.santubabu.nextplayerpro.core.domain

import android.content.Context
import android.net.Uri
import dagger.hilt.android.qualifiers.ApplicationContext
import com.santubabu.nextplayerpro.core.common.Dispatcher
import com.santubabu.nextplayerpro.core.common.NextDispatchers
import com.santubabu.nextplayerpro.core.common.extensions.getPath
import com.santubabu.nextplayerpro.core.data.repository.PreferencesRepository
import com.santubabu.nextplayerpro.core.model.MediaViewMode
import com.santubabu.nextplayerpro.core.model.Video
import java.io.File
import javax.inject.Inject
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.withContext

class GetSortedPlaylistUseCase @Inject constructor(
    private val getSortedVideosUseCase: GetSortedVideosUseCase,
    private val preferencesRepository: PreferencesRepository,
    @ApplicationContext private val context: Context,
    @Dispatcher(NextDispatchers.Default) private val defaultDispatcher: CoroutineDispatcher,
) {
    suspend operator fun invoke(uri: Uri): List<Video> = withContext(defaultDispatcher) {
        val path = context.getPath(uri) ?: return@withContext emptyList()
        val parent = File(path).parent.takeIf {
            preferencesRepository.applicationPreferences.first().mediaViewMode != MediaViewMode.VIDEOS
        }

        getSortedVideosUseCase.invoke(parent).first()
    }
}
