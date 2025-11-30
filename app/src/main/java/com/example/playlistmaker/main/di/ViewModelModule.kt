package com.example.playlistmaker.main.di

import com.example.playlistmaker.main.presentation.MainViewModel
import com.example.playlistmaker.main.presentation.MainViewModelImpl
import com.example.playlistmaker.player.presentation.PlayerViewModel
import com.example.playlistmaker.player.presentation.PlayerViewModelImpl
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val mainViewModelModule = module {

    viewModel<MainViewModel> {
        MainViewModelImpl(get(), get())
    }
}