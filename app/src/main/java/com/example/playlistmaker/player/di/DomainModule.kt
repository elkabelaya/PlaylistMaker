package com.example.playlistmaker.player.di

import com.example.playlistmaker.player.domain.api.PlayerInteractor
import com.example.playlistmaker.player.domain.api.PlayerNavigatorInteractor
import com.example.playlistmaker.player.domain.api.PlayerPlaylistsInteractor
import com.example.playlistmaker.player.domain.impl.PlayerInteractorImpl
import com.example.playlistmaker.player.domain.impl.PlayerNavigatorInteractorImpl
import com.example.playlistmaker.player.domain.impl.PlayerPlaylistsInteractorImpl
import org.koin.dsl.module

val playerDomainModule = module {
    factory<PlayerInteractor> { PlayerInteractorImpl(get(), get()) }
    factory<PlayerPlaylistsInteractor> { PlayerPlaylistsInteractorImpl(get(), get()) }
    factory<PlayerNavigatorInteractor> {
        PlayerNavigatorInteractorImpl(get(), get())
    }
}
