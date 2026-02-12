package com.example.playlistmaker.newplaylist.di

import com.example.playlistmaker.media.presentation.FavoritesViewModel
import com.example.playlistmaker.media.presentation.FavoritesViewModelImpl
import com.example.playlistmaker.media.presentation.PlaylistsViewModel
import com.example.playlistmaker.media.presentation.PlaylistsViewModelImpl
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val newplaylistViewModelModule = module {

    viewModel<FavoritesViewModel> { params ->
        FavoritesViewModelImpl(get(), get{params}, get())
    }

    viewModel<PlaylistsViewModel> {
        PlaylistsViewModelImpl(get())
    }
}