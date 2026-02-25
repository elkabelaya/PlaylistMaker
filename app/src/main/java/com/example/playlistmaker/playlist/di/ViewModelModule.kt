package com.example.playlistmaker.playlist.di

import com.example.playlistmaker.player.presentation.PlayerViewModel
import com.example.playlistmaker.player.presentation.PlayerViewModelImpl
import com.example.playlistmaker.playlist.presentation.PlaylistViewModel
import com.example.playlistmaker.playlist.presentation.PlaylistViewModelImpl
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val playlistViewModelModule = module {

    viewModel<PlaylistViewModel> { parameters ->
        PlaylistViewModelImpl(get{parameters}, get(), parameters.get())
    }
}