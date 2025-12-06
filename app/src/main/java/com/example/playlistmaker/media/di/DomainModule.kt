package com.example.playlistmaker.media.di

import com.example.playlistmaker.main.domain.api.MainNavigatorInteractor
import com.example.playlistmaker.main.domain.impl.MainNavigatorInteractorImpl
import com.example.playlistmaker.player.domain.api.PlayerInteractor
import com.example.playlistmaker.player.domain.impl.PlayerInteractorImpl
import org.koin.dsl.module

val mediaDomainModule = module {
//     factory<PlayerInteractor> { PlayerInteractorImpl(get(), get()) }
//    factory<MainNavigatorInteractor> { MainNavigatorInteractorImpl(get(), get()) }
}
