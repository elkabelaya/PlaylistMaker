package com.example.playlistmaker.main.di

import com.example.playlistmaker.main.domain.repository.MainNavigatorRepository
import com.example.playlistmaker.main.presentation.repository.MainNavigatorRepositoryImpl
import org.koin.dsl.module

val mainPresentationModule = module {
    factory<MainNavigatorRepository>{ MainNavigatorRepositoryImpl() }
}
