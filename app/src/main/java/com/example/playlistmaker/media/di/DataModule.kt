package com.example.playlistmaker.media.di

import com.example.playlistmaker.common.data.repository.ThemeRepositoryImpl
import com.example.playlistmaker.common.domain.repository.ThemeRepository
import com.example.playlistmaker.media.data.repository.MediaFavoritesRepositoryImpl
import com.example.playlistmaker.media.data.repository.MediaPlaylistsRepositoryImpl
import com.example.playlistmaker.media.domain.repository.MediaFavoritesRepository
import com.example.playlistmaker.media.domain.repository.MediaPlaylistsRepository
import com.example.playlistmaker.player.data.repository.PlayerRepositoryImpl
import com.example.playlistmaker.player.domain.repository.PlayerRepository
import org.koin.dsl.module

val mediaDataModule = module {
    single<MediaFavoritesRepository>{ MediaFavoritesRepositoryImpl() }
    single<MediaPlaylistsRepository>{ MediaPlaylistsRepositoryImpl() }
}
