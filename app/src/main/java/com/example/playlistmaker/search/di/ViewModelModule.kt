package com.example.playlistmaker.search.di

import com.example.playlistmaker.search.presentation.SearchViewModel
import com.example.playlistmaker.search.presentation.SearchViewModelImpl
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val searchViewModelModule = module {

    viewModel <SearchViewModel>{
        SearchViewModelImpl(get(), get(), get())
    }
}