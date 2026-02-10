package com.example.playlistmaker.media.di

import com.example.playlistmaker.media.domain.api.MediaFavoritesInteractor
import com.example.playlistmaker.media.domain.api.MediaNavigatorInteractor
import com.example.playlistmaker.media.domain.api.MediaPlaylistsInteractor
import com.example.playlistmaker.media.domain.impl.MediaFavoritesInteractorImpl
import com.example.playlistmaker.media.domain.impl.MediaNavigatorInteractorImpl
import com.example.playlistmaker.media.domain.impl.MediaPlaylistsInteractorImpl
import com.example.playlistmaker.search.domain.api.SearchNavigatorInteractor
import com.example.playlistmaker.search.domain.impl.SearchNavigatorInteractorImpl
import org.koin.dsl.module

val mediaDomainModule = module {
    factory<MediaFavoritesInteractor> { MediaFavoritesInteractorImpl(get(), get()) }
    factory<MediaPlaylistsInteractor> { MediaPlaylistsInteractorImpl(get(), get()) }
    factory<MediaNavigatorInteractor> { params ->
        MediaNavigatorInteractorImpl(get { params }, get())
    }
}
