package com.example.playlistmaker.common.di

import android.content.Context
import com.example.playlistmaker.common.data.mapper.TracksMapperImpl
import com.example.playlistmaker.search.data.network.TracksRetrofitNetworkClient
import com.example.playlistmaker.common.data.repository.LoopRepositoryImpl
import com.example.playlistmaker.common.data.repository.NavigatorRepositoryImpl
import com.example.playlistmaker.player.data.repository.PlayerRepositoryImpl
import com.example.playlistmaker.common.data.repository.PreferencesRepositoryImpl
import com.example.playlistmaker.common.data.repository.ThemeRepositoryImpl
import com.example.playlistmaker.common.data.repository.TracksMapper
import com.example.playlistmaker.search.data.repository.TracksRepositoryImpl
import com.example.playlistmaker.common.domain.api.ModeInteractor
import com.example.playlistmaker.player.domain.api.PlayerInteractor
import com.example.playlistmaker.search.domain.api.SearchInteractor
import com.example.playlistmaker.common.domain.impl.ClickDebounceUseCaseImpl
import com.example.playlistmaker.search.domain.impl.HistoryUseCaseImpl
import com.example.playlistmaker.search.domain.impl.InputDebounceUseCaseImpl
import com.example.playlistmaker.common.domain.impl.ModeInteractorImpl
import com.example.playlistmaker.player.domain.impl.PlayerInteractorImpl
import com.example.playlistmaker.search.domain.impl.SearchInteractorImpl
import com.example.playlistmaker.search.domain.impl.GetTracksUseCaseImpl
import com.example.playlistmaker.common.domain.repository.LoopRepository
import com.example.playlistmaker.common.domain.repository.NavigatorRepository
import com.example.playlistmaker.player.domain.repository.PlayerRepository
import com.example.playlistmaker.common.domain.repository.PreferencesRepository
import com.example.playlistmaker.common.domain.repository.ThemeRepository
import com.example.playlistmaker.search.domain.repository.TracksRepository
import com.example.playlistmaker.common.domain.use_case.ClickDebounceUseCase
import com.example.playlistmaker.main.domain.api.MainNavigatorInteractor
import com.example.playlistmaker.main.domain.impl.MainNavigatorInteractorImpl
import com.example.playlistmaker.main.domain.repository.MainNavigatorRepository
import com.example.playlistmaker.main.presentation.repository.MainNavigatorRepositoryImpl
import com.example.playlistmaker.search.domain.use_case.HistoryUseCase
import com.example.playlistmaker.search.domain.use_case.GetTracksUseCase
import com.example.playlistmaker.search.domain.use_case.InputDebounceUseCase
import com.example.playlistmaker.player.domain.repository.PlayerDefaultsRepository
import com.example.playlistmaker.player.presentation.repository.PlayerDefaultsRepositoryImpl
import com.example.playlistmaker.search.domain.api.SearchNavigatorInteractor
import com.example.playlistmaker.search.domain.impl.SearchNavigatorInteractorImpl
import com.example.playlistmaker.search.domain.repository.SearchNavigatorRepository
import com.example.playlistmaker.search.presentation.repository.SearchNavigatorRepositoryImpl
import com.example.playlistmaker.settings.domain.api.SettingsNavigatorInteractor
import com.example.playlistmaker.settings.domain.impl.SettingsNavigatorInteractorImpl
import com.example.playlistmaker.settings.domain.repository.SettingsNavigatorRepository
import com.example.playlistmaker.settings.presentation.repository.SettingsNavigatorRepositoryImpl
import org.koin.java.KoinJavaComponent.inject

object Creator {

    private fun getLoopRepository(): LoopRepository {
        return LoopRepositoryImpl()
    }

    fun provideClickDebounceUseCase(): ClickDebounceUseCase {
        return ClickDebounceUseCaseImpl(getLoopRepository())
    }

    fun getNavigatorRepository(context: Context): NavigatorRepository {
        return NavigatorRepositoryImpl(context)
    }


    fun getMainNavigatorRepository(): MainNavigatorRepository {
        return MainNavigatorRepositoryImpl()
    }

    fun provideMainNavigatorInteractor(context: Context): MainNavigatorInteractor {
        return MainNavigatorInteractorImpl(
            getNavigatorRepository(context),
            getMainNavigatorRepository()
        )
    }

}