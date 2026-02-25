package com.example.playlistmaker.playlist.di

import com.example.playlistmaker.player.domain.repository.PlayerDefaultsRepository
import com.example.playlistmaker.player.presentation.repository.PlayerDefaultsRepositoryImpl
import com.example.playlistmaker.playlist.domain.repository.PlaylistDescriptionRepository
import com.example.playlistmaker.playlist.domain.repository.PlaylistNavigatorRepository
import com.example.playlistmaker.playlist.presentation.repository.PlaylistDescriptionRepositoryImpl
import com.example.playlistmaker.playlist.presentation.repository.PlaylistNavigatorRepositoryImpl
import org.koin.dsl.module

val playlistPresentationModule = module {
    factory<PlaylistNavigatorRepository>{ PlaylistNavigatorRepositoryImpl(get()) }
    factory<PlaylistDescriptionRepository>{ PlaylistDescriptionRepositoryImpl(get())}
}
