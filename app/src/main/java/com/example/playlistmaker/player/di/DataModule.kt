package com.example.playlistmaker.player.di

import com.example.playlistmaker.player.domain.repository.PlayerNavigatorRepository
import com.example.playlistmaker.player.data.repository.PlayerRepositoryImpl
import com.example.playlistmaker.player.domain.repository.PlayerRepository
import com.example.playlistmaker.player.presentation.repository.PlayerNavigatorRepositoryImpl
import org.koin.dsl.module

val playerDataModule = module {
    factory<PlayerRepository> { PlayerRepositoryImpl() }
    factory<PlayerNavigatorRepository> { PlayerNavigatorRepositoryImpl() }
}
