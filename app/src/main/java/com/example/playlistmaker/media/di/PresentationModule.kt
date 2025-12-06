package com.example.playlistmaker.media.di

import com.example.playlistmaker.main.domain.repository.MainNavigatorRepository
import com.example.playlistmaker.main.presentation.repository.MainNavigatorRepositoryImpl
import org.koin.dsl.module

val mediaPresentationModule = module {
    //factory<MainNavigatorRepository>{ MainNavigatorRepositoryImpl() }
}
