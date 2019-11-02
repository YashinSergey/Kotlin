package com.example.kotlin

import android.app.Application
import androidx.multidex.MultiDex
import com.github.ajalt.timberkt.Timber

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        Timber.plant(timber.log.Timber.DebugTree())
        MultiDex.install(this)
    }
}