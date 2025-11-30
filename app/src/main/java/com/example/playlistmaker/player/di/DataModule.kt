package com.example.playlistmaker.player.di

import com.example.playlistmaker.player.data.repository.PlayerRepositoryImpl
import com.example.playlistmaker.player.domain.repository.PlayerRepository
import org.koin.dsl.module

val playerDataModule = module {
    factory<PlayerRepository> { PlayerRepositoryImpl() }
}
