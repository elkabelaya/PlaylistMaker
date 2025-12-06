package com.example.playlistmaker.media.di

import com.example.playlistmaker.media.presentation.FavoritesViewModel
import com.example.playlistmaker.media.presentation.FavoritesViewModelImpl
import com.example.playlistmaker.media.presentation.PlaylistsViewModel
import com.example.playlistmaker.media.presentation.PlaylistsViewModelImpl
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val mediaViewModelModule = module {
    viewModel<FavoritesViewModel> {
        FavoritesViewModelImpl()
    }

    viewModel<PlaylistsViewModel> {
        PlaylistsViewModelImpl()
    }
}