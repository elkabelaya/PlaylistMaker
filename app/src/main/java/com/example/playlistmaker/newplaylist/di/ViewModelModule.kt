package com.example.playlistmaker.newplaylist.di

import com.example.playlistmaker.media.presentation.FavoritesViewModel
import com.example.playlistmaker.media.presentation.FavoritesViewModelImpl
import com.example.playlistmaker.media.presentation.PlaylistsViewModel
import com.example.playlistmaker.media.presentation.PlaylistsViewModelImpl
import com.example.playlistmaker.newplaylist.presentation.NewPlaylistViewModel
import com.example.playlistmaker.newplaylist.presentation.NewPlaylistViewModelImpl
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val newplaylistViewModelModule = module {

    viewModel<NewPlaylistViewModel> {
        NewPlaylistViewModelImpl(get(), get())
    }

    viewModel<PlaylistsViewModel> { params ->
        PlaylistsViewModelImpl(get(), get{params})
    }
}