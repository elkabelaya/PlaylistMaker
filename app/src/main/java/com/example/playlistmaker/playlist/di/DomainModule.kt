package com.example.playlistmaker.playlist.di

import com.example.playlistmaker.playlist.domain.api.PlaylistInteractor
import com.example.playlistmaker.playlist.domain.api.PlaylistNavigatorInteractor
import com.example.playlistmaker.playlist.domain.impl.PlaylistInteractorImpl
import com.example.playlistmaker.playlist.domain.impl.PlaylistNavigatorInteractorImpl
import org.koin.dsl.module

val playlistDomainModule = module {
    factory<PlaylistInteractor> { parameters ->
        PlaylistInteractorImpl(get(),  get(),parameters.get())
    }
    factory<PlaylistNavigatorInteractor>{ PlaylistNavigatorInteractorImpl(get(), get(), get()) }
}
