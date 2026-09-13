package com.flatcode.simplecomposeapps

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import io.selimdawa.multicolors.MultiColorManager
import timber.log.Timber

@HiltAndroidApp
class MainApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        MultiColorManager.init(this)
        Timber.plant(Timber.DebugTree())
    }
}