package com.example.playlistmaker.settings.di

import com.example.playlistmaker.search.presentation.SearchViewModel
import com.example.playlistmaker.search.presentation.SearchViewModelImpl
import com.example.playlistmaker.settings.presentation.SettingsViewModel
import com.example.playlistmaker.settings.presentation.SettingsViewModelImpl
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val settingsViewModelModule = module {

    viewModel<SettingsViewModel> {
        SettingsViewModelImpl(get(), get())
    }
}