package com.example.playlistmaker.media.di

import com.example.playlistmaker.media.domain.repository.MediaFavoritesErrorRepository
import com.example.playlistmaker.media.domain.repository.MediaNavigatorRepository
import com.example.playlistmaker.media.domain.repository.MediaPlaylistsErrorRepository
import com.example.playlistmaker.media.presentation.repository.MediaFavoritesErrorRepositoryImpl
import com.example.playlistmaker.media.presentation.repository.MediaNavigatorRepositoryImpl
import com.example.playlistmaker.media.presentation.repository.MediaPlaylistsErrorsRepositoryImpl
import com.example.playlistmaker.search.domain.repository.SearchNavigatorRepository
import com.example.playlistmaker.search.presentation.repository.SearchNavigatorRepositoryImpl
import org.koin.dsl.module

val mediaPresentationModule = module {
    factory<MediaFavoritesErrorRepository>{ MediaFavoritesErrorRepositoryImpl(get()) }
    factory<MediaPlaylistsErrorRepository>{ MediaPlaylistsErrorsRepositoryImpl(get()) }
    factory<MediaNavigatorRepository>{ MediaNavigatorRepositoryImpl() }
}
