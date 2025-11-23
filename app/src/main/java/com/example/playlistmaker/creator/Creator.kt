package com.example.playlistmaker.creator

import android.content.Context
import com.example.playlistmaker.data.mapper.TracksMapperImpl
import com.example.playlistmaker.data.network.TracksRetrofitNetworkClient
import com.example.playlistmaker.data.repository.LoopRepositoryImpl
import com.example.playlistmaker.player.data.repository.PlayerRepositoryImpl
import com.example.playlistmaker.data.repository.PreferencesRepositoryImpl
import com.example.playlistmaker.data.repository.ThemeRepositoryImpl
import com.example.playlistmaker.data.repository.TracksMapper
import com.example.playlistmaker.data.repository.TracksRepositoryImpl
import com.example.playlistmaker.domain.use_case.HistoryUseCase
import com.example.playlistmaker.domain.api.ModeInteractor
import com.example.playlistmaker.player.domain.api.PlayerInteractor
import com.example.playlistmaker.domain.api.SearchInteractor
import com.example.playlistmaker.domain.impl.ClickDebounceUseCaseImpl
import com.example.playlistmaker.domain.impl.HistoryUseCaseImpl
import com.example.playlistmaker.domain.impl.InputDebounceUseCaseImpl
import com.example.playlistmaker.domain.impl.ModeInteractorImpl
import com.example.playlistmaker.player.domain.impl.PlayerInteractorImpl
import com.example.playlistmaker.domain.impl.SearchInteractorImpl
import com.example.playlistmaker.domain.impl.GetTracksUseCaseImpl
import com.example.playlistmaker.domain.repository.LoopRepository
import com.example.playlistmaker.player.domain.repository.PlayerRepository
import com.example.playlistmaker.domain.repository.PreferencesRepository
import com.example.playlistmaker.domain.repository.ThemeRepository
import com.example.playlistmaker.domain.repository.TracksRepository
import com.example.playlistmaker.domain.use_case.ClickDebounceUseCase
import com.example.playlistmaker.domain.use_case.GetTracksUseCase
import com.example.playlistmaker.domain.use_case.InputDebounceUseCase
import com.example.playlistmaker.player.domain.api.PlayerState

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

    fun providePlayerInteractor(url: String, onState:(PlayerState) -> Unit): PlayerInteractor {
        val repository = getPlayerRepository(url)
        val interactor = PlayerInteractorImpl(repository, onState)
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

    fun provideSearchInteractor(context: Context, onState:(Int) -> Unit): SearchInteractor {

        return SearchInteractorImpl(
            onState,
            provideInputDebounceUseCase(),
            provideGetTracksUseCase(),
            provideHistoryUseCase(context),
            getLoopRepository()
        )
    }
}