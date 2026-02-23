package com.example.playlistmaker

import android.app.Application
import android.content.Context
import com.example.playlistmaker.common.di.commonModules
import com.example.playlistmaker.common.domain.api.ModeInteractor
import com.example.playlistmaker.common.presentation.utils.LocaleHelper
import org.koin.android.ext.android.get
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext.startKoin


class App : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@App)
            modules(commonModules)
        }

        val modeInteractor: ModeInteractor = get()
        modeInteractor.startTheme()

    }
    override fun attachBaseContext(newBase: Context) {
        super.attachBaseContext(LocaleHelper.onAttach(newBase, "ru"))
    }
}