package com.example.playlistmaker

import android.app.Application
import com.example.playlistmaker.common.di.Creator
import com.example.playlistmaker.common.di.commonModules
import com.example.playlistmaker.common.domain.api.ModeInteractor
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext.startKoin

class App : Application() {
    lateinit var modeInteractor: ModeInteractor

    override fun onCreate() {
        super.onCreate()
        modeInteractor = Creator.provideModeInteractor(this)
        modeInteractor.startTheme()

        startKoin{
            androidContext(this@App)
            modules(commonModules)
        }
    }
}