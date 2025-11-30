package com.example.playlistmaker

import android.app.Application
import com.example.playlistmaker.creator.Creator
import com.example.playlistmaker.common.domain.api.ModeInteractor

class App : Application() {
    lateinit var modeInteractor: ModeInteractor

    override fun onCreate() {
        super.onCreate()
        modeInteractor = Creator.provideModeInteractor(this)
        modeInteractor.startTheme()
    }
}