package com.example.playlistmaker.media.di

import com.example.playlistmaker.media.domain.repository.MediaFavoritesErrorRepository
import com.example.playlistmaker.media.domain.repository.MediaPlaylistsErrorRepository
import com.example.playlistmaker.media.presentation.repository.MediaFavoritesErrorRepositoryImpl
import com.example.playlistmaker.media.presentation.repository.MediaPlaylistsErrorsRepositoryImpl
import org.koin.dsl.module

val mediaPresentationModule = module {
    factory<MediaFavoritesErrorRepository>{ MediaFavoritesErrorRepositoryImpl(get()) }
    factory<MediaPlaylistsErrorRepository>{ MediaPlaylistsErrorsRepositoryImpl(get()) }
}
