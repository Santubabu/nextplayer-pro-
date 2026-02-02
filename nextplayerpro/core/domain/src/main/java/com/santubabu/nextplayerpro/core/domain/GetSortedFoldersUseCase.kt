package com.santubabu.nextplayerpro.core.domain

import com.santubabu.nextplayerpro.core.common.Dispatcher
import com.santubabu.nextplayerpro.core.common.NextDispatchers
import com.santubabu.nextplayerpro.core.data.repository.MediaRepository
import com.santubabu.nextplayerpro.core.data.repository.PreferencesRepository
import com.santubabu.nextplayerpro.core.model.Folder
import com.santubabu.nextplayerpro.core.model.Sort
import javax.inject.Inject
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flowOn

class GetSortedFoldersUseCase @Inject constructor(
    private val mediaRepository: MediaRepository,
    private val preferencesRepository: PreferencesRepository,
    @Dispatcher(NextDispatchers.Default) private val defaultDispatcher: CoroutineDispatcher,
) {

    operator fun invoke(): Flow<List<Folder>> {
        return combine(
            mediaRepository.getFoldersFlow(),
            preferencesRepository.applicationPreferences,
        ) { folders, preferences ->

            val nonExcludedDirectories = folders.filter {
                it.mediaList.isNotEmpty() && it.path !in preferences.excludeFolders
            }

            val sort = Sort(by = preferences.sortBy, order = preferences.sortOrder)
            nonExcludedDirectories.sortedWith(sort.folderComparator())
        }.flowOn(defaultDispatcher)
    }
}
