package com.cmuhatia.android.keepup

import android.app.Application
import timber.log.Timber

class KeepupApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
    }
}