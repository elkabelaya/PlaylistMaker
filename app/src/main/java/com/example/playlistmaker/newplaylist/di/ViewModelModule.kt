package com.example.playlistmaker.newplaylist.di

import com.example.playlistmaker.newplaylist.presentation.NewPlaylistViewModel
import com.example.playlistmaker.newplaylist.presentation.NewPlaylistViewModelImpl
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val newplaylistViewModelModule = module {

    viewModel<NewPlaylistViewModel> {
        NewPlaylistViewModelImpl(get())
    }
}