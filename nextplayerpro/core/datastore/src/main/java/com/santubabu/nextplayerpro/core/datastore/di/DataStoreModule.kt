package com.santubabu.nextplayerpro.core.datastore.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.core.DataStoreFactory
import androidx.datastore.dataStoreFile
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import com.santubabu.nextplayerpro.core.common.Dispatcher
import com.santubabu.nextplayerpro.core.common.NextDispatchers
import com.santubabu.nextplayerpro.core.common.di.ApplicationScope
import com.santubabu.nextplayerpro.core.datastore.serializer.ApplicationPreferencesSerializer
import com.santubabu.nextplayerpro.core.datastore.serializer.PlayerPreferencesSerializer
import com.santubabu.nextplayerpro.core.model.ApplicationPreferences
import com.santubabu.nextplayerpro.core.model.PlayerPreferences
import javax.inject.Singleton
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope

private const val APP_PREFERENCES_DATASTORE_FILE = "app_preferences.json"
private const val PLAYER_PREFERENCES_DATASTORE_FILE = "player_preferences.json"

@Module
@InstallIn(SingletonComponent::class)
object DataStoreModule {

    @Provides
    @Singleton
    fun provideAppPreferencesDataStore(
        @ApplicationContext context: Context,
        @Dispatcher(NextDispatchers.IO) ioDispatcher: CoroutineDispatcher,
        @ApplicationScope scope: CoroutineScope,
    ): DataStore<ApplicationPreferences> {
        return DataStoreFactory.create(
            serializer = ApplicationPreferencesSerializer,
            scope = CoroutineScope(scope.coroutineContext + ioDispatcher),
            produceFile = { context.dataStoreFile(APP_PREFERENCES_DATASTORE_FILE) },
        )
    }

    @Provides
    @Singleton
    fun providePlayerPreferencesDataStore(
        @ApplicationContext applicationContext: Context,
        @Dispatcher(NextDispatchers.IO) ioDispatcher: CoroutineDispatcher,
        @ApplicationScope scope: CoroutineScope,
    ): DataStore<PlayerPreferences> {
        return DataStoreFactory.create(
            serializer = PlayerPreferencesSerializer,
            scope = CoroutineScope(scope.coroutineContext + ioDispatcher),
            produceFile = { applicationContext.dataStoreFile(PLAYER_PREFERENCES_DATASTORE_FILE) },
        )
    }
}
