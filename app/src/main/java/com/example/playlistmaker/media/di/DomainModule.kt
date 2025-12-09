package com.example.playlistmaker.media.di

import com.example.playlistmaker.media.domain.api.MediaFavoritesInteractor
import com.example.playlistmaker.media.domain.api.MediaPlaylistsInteractor
import com.example.playlistmaker.media.domain.impl.MediaFavoritesInteractorImpl
import com.example.playlistmaker.media.domain.impl.MediaPlaylistsInteractorImpl
import org.koin.dsl.module

val mediaDomainModule = module {
    factory<MediaFavoritesInteractor> { MediaFavoritesInteractorImpl(get(), get()) }
    factory<MediaPlaylistsInteractor> { MediaPlaylistsInteractorImpl(get(), get()) }
}
