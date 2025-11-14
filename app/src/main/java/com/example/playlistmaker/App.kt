package com.example.playlistmaker

import android.app.Application
import android.content.Context
import androidx.appcompat.app.AppCompatDelegate
import com.example.playlistmaker.creator.Creator
import com.example.playlistmaker.domain.api.ModeInteractor

class App : Application() {
    lateinit var modeInteractor: ModeInteractor

    override fun onCreate() {
        super.onCreate()
        modeInteractor = Creator.provideModeInteractor(this)
        modeInteractor.startTheme()
    }
}