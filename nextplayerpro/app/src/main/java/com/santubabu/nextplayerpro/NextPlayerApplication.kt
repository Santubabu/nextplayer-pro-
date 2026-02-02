package com.santubabu.nextplayerpro

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import com.santubabu.nextplayerpro.core.common.di.ApplicationScope
import com.santubabu.nextplayerpro.core.data.repository.PreferencesRepository
import com.santubabu.nextplayerpro.crash.CrashActivity
import com.santubabu.nextplayerpro.crash.GlobalExceptionHandler
import javax.inject.Inject
import kotlinx.coroutines.CoroutineScope

@HiltAndroidApp
class NextPlayerApplication : Application() {

    @Inject
    lateinit var preferencesRepository: PreferencesRepository

    @Inject
    @ApplicationScope
    lateinit var applicationScope: CoroutineScope

    override fun onCreate() {
        super.onCreate()
        Thread.setDefaultUncaughtExceptionHandler(GlobalExceptionHandler(applicationContext, CrashActivity::class.java))
    }
}
