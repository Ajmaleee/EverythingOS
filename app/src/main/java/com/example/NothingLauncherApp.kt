package com.example

import android.app.Application
import com.example.data.LauncherDatabase
import com.example.data.SettingsRepository
import com.example.icon.MonochromeIconEngine

class NothingLauncherApp : Application() {

    lateinit var database: LauncherDatabase
        private set

    lateinit var settingsRepository: SettingsRepository
        private set

    lateinit var iconEngine: MonochromeIconEngine
        private set

    override fun onCreate() {
        super.onCreate()
        database = LauncherDatabase.getInstance(this)
        settingsRepository = SettingsRepository(this)
        iconEngine = MonochromeIconEngine(this)
    }
}
