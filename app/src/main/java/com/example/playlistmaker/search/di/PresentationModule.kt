package com.example.playlistmaker.search.di

import com.example.playlistmaker.search.domain.repository.SearchNavigatorRepository
import com.example.playlistmaker.search.presentation.repository.SearchNavigatorRepositoryImpl
import org.koin.dsl.module

val searchPresentationModule = module {
    factory<SearchNavigatorRepository>{ SearchNavigatorRepositoryImpl() }
}
