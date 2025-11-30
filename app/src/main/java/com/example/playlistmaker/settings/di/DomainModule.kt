package com.example.playlistmaker.settings.di

import com.example.playlistmaker.settings.domain.api.SettingsNavigatorInteractor
import com.example.playlistmaker.settings.domain.impl.SettingsNavigatorInteractorImpl
import org.koin.dsl.module

val settingsDomainModule = module {
     factory<SettingsNavigatorInteractor> { SettingsNavigatorInteractorImpl(get(), get()) }
}
