package com.example.playlistmaker.common.di

import com.example.playlistmaker.player.domain.api.PlayerFavoriteInteractor
import com.example.playlistmaker.common.domain.api.ModeInteractor
import com.example.playlistmaker.common.domain.impl.ClickDebounceUseCaseImpl
import com.example.playlistmaker.player.domain.impl.PlayerFavoriteInteractorImpl
import com.example.playlistmaker.common.domain.impl.ModeInteractorImpl
import com.example.playlistmaker.common.domain.use_case.ClickDebounceUseCase

import org.koin.dsl.module

val commonDomainModule = module {
    factory<ClickDebounceUseCase>{ ClickDebounceUseCaseImpl(get()) }
    factory<ModeInteractor>{ ModeInteractorImpl(get(), get()) }
    factory<PlayerFavoriteInteractor> { PlayerFavoriteInteractorImpl(get()) }

}
