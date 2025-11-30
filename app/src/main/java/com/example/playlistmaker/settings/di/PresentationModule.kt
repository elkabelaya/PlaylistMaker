package com.example.playlistmaker.settings.di

import com.example.playlistmaker.settings.domain.repository.SettingsNavigatorRepository
import com.example.playlistmaker.settings.presentation.repository.SettingsNavigatorRepositoryImpl
import org.koin.dsl.module

val settingsPresentationModule = module {
    factory<SettingsNavigatorRepository>{ SettingsNavigatorRepositoryImpl(get()) }
}
