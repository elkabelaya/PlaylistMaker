package com.example.playlistmaker.creator

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

object Creator {
    private fun getTracksRepository(): TracksRepository {
        return TracksRepositoryImpl(TracksRetrofitNetworkClient(), getTracksMapper())
    }

    private fun getTracksMapper(): TracksMapper {
        return TracksMapperImpl()
    }
    private fun getPreferencesRepository(context: Context): PreferencesRepository {
        return PreferencesRepositoryImpl(context, getTracksMapper())
    }

    private fun getThemeRepository(): ThemeRepository {
        return ThemeRepositoryImpl()
    }

    fun provideModeInteractor(context: Context): ModeInteractor {
        return ModeInteractorImpl(getPreferencesRepository(context),getThemeRepository())
    }

    private fun getPlayerRepository(url: String): PlayerRepository {
        return PlayerRepositoryImpl(url)
    }

    private fun getPlayerDefaultsRepository(context: Context): PlayerDefaultsRepository {
        return PlayerDefaultsRepositoryImpl(context)
    }

    fun providePlayerInteractor(url: String, context: Context): PlayerInteractor {
        val repository = getPlayerRepository(url)
        val interactor = PlayerInteractorImpl(repository, getPlayerDefaultsRepository(context))
        repository.onPrepared = { interactor.onPrepared() }
        repository.onComplete = { interactor.onComplete() }
        return interactor
    }

    private fun getLoopRepository(): LoopRepository {
        return LoopRepositoryImpl()
    }

    fun provideClickDebounceUseCase(): ClickDebounceUseCase {
        return ClickDebounceUseCaseImpl(getLoopRepository())
    }

    fun provideInputDebounceUseCase(): InputDebounceUseCase {
        return InputDebounceUseCaseImpl(getLoopRepository())
    }

    fun provideGetTracksUseCase(): GetTracksUseCase {
        return GetTracksUseCaseImpl(getTracksRepository())
    }

    fun provideHistoryUseCase(context: Context): HistoryUseCase {
        return HistoryUseCaseImpl(getPreferencesRepository(context), 10)
    }

    fun getNavigatorRepository(context: Context): NavigatorRepository {
        return NavigatorRepositoryImpl(context)
    }
    fun getSearchNavigatorRepository(): SearchNavigatorRepository {
        return SearchNavigatorRepositoryImpl()
    }
    fun provideSearchNavigatorInteractor(context: Context): SearchNavigatorInteractor {
        return SearchNavigatorInteractorImpl(getNavigatorRepository(context), getSearchNavigatorRepository())
    }

    fun provideSearchInteractor(context: Context): SearchInteractor {
        return SearchInteractorImpl(
            provideInputDebounceUseCase(),
            provideGetTracksUseCase(),
            provideHistoryUseCase(context),
            getLoopRepository()
        )
    }

    fun getSettingsNavigatorRepository(context: Context): SettingsNavigatorRepository {
        return SettingsNavigatorRepositoryImpl(context)
    }
    fun provideSettingsNavigatorInteractor(context: Context): SettingsNavigatorInteractor {
        return SettingsNavigatorInteractorImpl(getNavigatorRepository(context),getSettingsNavigatorRepository(context))
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