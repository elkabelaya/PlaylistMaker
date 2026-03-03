package com.example.playlistmaker.playlist.di

import com.example.playlistmaker.player.domain.repository.PlayerNavigatorRepository
import com.example.playlistmaker.player.data.repository.PlayerRepositoryImpl
import com.example.playlistmaker.player.domain.repository.PlayerRepository
import com.example.playlistmaker.player.presentation.repository.PlayerNavigatorRepositoryImpl
import org.koin.dsl.module

val playlistDataModule = module {
//    factory<PlayerRepository> { PlayerRepositoryImpl() }
//    factory<PlayerNavigatorRepository> { PlayerNavigatorRepositoryImpl() }
}
