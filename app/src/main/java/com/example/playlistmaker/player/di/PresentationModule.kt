package com.example.playlistmaker.player.di

import com.example.playlistmaker.player.domain.repository.PlayerDefaultsRepository
import com.example.playlistmaker.player.presentation.repository.PlayerDefaultsRepositoryImpl
import com.example.playlistmaker.settings.domain.repository.SettingsNavigatorRepository
import com.example.playlistmaker.settings.presentation.repository.SettingsNavigatorRepositoryImpl
import org.koin.dsl.module

val playerPresentationModule = module {
    factory<PlayerDefaultsRepository>{ PlayerDefaultsRepositoryImpl(get()) }
}
