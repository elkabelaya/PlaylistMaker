package com.example.playlistmaker

import android.app.Application
import com.example.playlistmaker.common.di.commonModules
import com.example.playlistmaker.common.domain.api.ModeInteractor
import com.example.playlistmaker.search.di.searchModules
import org.koin.android.ext.android.get
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.fragment.koin.fragmentFactory
import org.koin.core.context.GlobalContext.startKoin
import org.koin.core.context.loadKoinModules
import org.koin.java.KoinJavaComponent.inject

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
}