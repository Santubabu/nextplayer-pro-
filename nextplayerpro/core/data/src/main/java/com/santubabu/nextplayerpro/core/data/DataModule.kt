package com.santubabu.nextplayerpro.core.data

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import com.santubabu.nextplayerpro.core.data.repository.LocalMediaRepository
import com.santubabu.nextplayerpro.core.data.repository.LocalPreferencesRepository
import com.santubabu.nextplayerpro.core.data.repository.MediaRepository
import com.santubabu.nextplayerpro.core.data.repository.PreferencesRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface DataModule {

    @Binds
    fun bindsMediaRepository(
        videoRepository: LocalMediaRepository,
    ): MediaRepository

    @Binds
    @Singleton
    fun bindsPreferencesRepository(
        preferencesRepository: LocalPreferencesRepository,
    ): PreferencesRepository
}
