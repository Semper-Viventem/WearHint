package com.example.konstantin.weartest

import android.app.Application
import org.koin.android.ext.android.startAndroidContext
import timber.log.Timber


class TheApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        initKoin()
        initLogging()
    }

    private fun initKoin() {
        startAndroidContext(this, allModules())
    }

    private fun initLogging() {
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }
}