package com.cmuhatia.android.keepup

import android.app.Application
import timber.log.Timber
import timber.log.Timber.DebugTree

class KeepupApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            Timber.plant(DebugTree())
        }
    }
}