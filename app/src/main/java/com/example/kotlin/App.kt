package com.example.kotlin

import android.app.Application
import android.content.Context
import androidx.multidex.MultiDex
import com.example.kotlin.koin.appModule
import com.example.kotlin.koin.authModule
import com.example.kotlin.koin.mainModule
import com.example.kotlin.koin.personModule
import com.github.ajalt.timberkt.Timber
import org.koin.android.ext.android.startKoin

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        Timber.plant(timber.log.Timber.DebugTree())
        startKoin(this, listOf(appModule, authModule, mainModule, personModule))
    }

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }
}