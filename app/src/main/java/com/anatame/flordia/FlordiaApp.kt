package com.anatame.flordia

import android.app.Application
import timber.log.Timber

class FlordiaApp: Application() {
    override fun onCreate() {
        super.onCreate()
        initTimber()
    }

    private fun initTimber() {
        if (BuildConfig.DEBUG) {
            Timber.plant(object : Timber.DebugTree() {
                override fun createStackElementTag(element: StackTraceElement): String? {
                    return super.createStackElementTag(element) + ":${element.methodName}:${element.lineNumber}"
                }
            })
        }
    }
}