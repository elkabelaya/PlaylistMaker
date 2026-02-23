package com.example.playlistmaker.media.di

import com.example.playlistmaker.media.presentation.favorites.MediaFavoritesViewModel
import com.example.playlistmaker.media.presentation.favorites.MediaFavoritesViewModelImpl
import com.example.playlistmaker.media.presentation.playlists.MediaPlaylistsViewModel
import com.example.playlistmaker.media.presentation.playlists.MediaPlaylistsViewModelImpl
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val mediaViewModelModule = module {

    viewModel<MediaFavoritesViewModel> {
        MediaFavoritesViewModelImpl(get(), get(), get())
    }

    viewModel<MediaPlaylistsViewModel> {
        MediaPlaylistsViewModelImpl(get(), get())
    }
}