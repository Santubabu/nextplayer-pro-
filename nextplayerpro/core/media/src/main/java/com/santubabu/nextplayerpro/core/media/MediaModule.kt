package com.santubabu.nextplayerpro.core.media

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import com.santubabu.nextplayerpro.core.media.services.LocalMediaService
import com.santubabu.nextplayerpro.core.media.services.MediaService
import com.santubabu.nextplayerpro.core.media.sync.LocalMediaInfoSynchronizer
import com.santubabu.nextplayerpro.core.media.sync.LocalMediaSynchronizer
import com.santubabu.nextplayerpro.core.media.sync.MediaInfoSynchronizer
import com.santubabu.nextplayerpro.core.media.sync.MediaSynchronizer
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface MediaModule {

    @Binds
    @Singleton
    fun bindsMediaSynchronizer(
        mediaSynchronizer: LocalMediaSynchronizer,
    ): MediaSynchronizer

    @Binds
    @Singleton
    fun bindsMediaInfoSynchronizer(
        mediaInfoSynchronizer: LocalMediaInfoSynchronizer,
    ): MediaInfoSynchronizer

    @Binds
    @Singleton
    fun bindMediaService(
        mediaService: LocalMediaService,
    ): MediaService
}
