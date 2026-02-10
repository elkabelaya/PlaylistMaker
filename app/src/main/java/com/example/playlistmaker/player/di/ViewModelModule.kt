package com.example.playlistmaker.player.di

import com.example.playlistmaker.player.presentation.PlayerViewModel
import com.example.playlistmaker.player.presentation.PlayerViewModelImpl
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val playerViewModelModule = module {

    viewModel<PlayerViewModel> { parameters ->
        PlayerViewModelImpl(get(),  get(),parameters.get())
    }
}