package com.example.playlistmaker.root.di

import com.example.playlistmaker.player.presentation.PlayerViewModel
import com.example.playlistmaker.player.presentation.PlayerViewModelImpl
import com.example.playlistmaker.root.presentation.RootViewModel
import com.example.playlistmaker.root.presentation.RootViewModelImpl
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val rootViewModelModule = module {
    viewModel<RootViewModel> {
        RootViewModelImpl(get())
    }
}