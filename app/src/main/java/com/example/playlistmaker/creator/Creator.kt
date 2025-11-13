package com.example.playlistmaker.creator

import android.content.Context
import com.example.playlistmaker.data.mapper.TracksMapperImpl
import com.example.playlistmaker.data.network.TracksRetrofitNetworkClient
import com.example.playlistmaker.data.repository.LoopRepositoryImpl
import com.example.playlistmaker.data.repository.PlayerRepositoryImpl
import com.example.playlistmaker.data.repository.PreferencesRepositoryImpl
import com.example.playlistmaker.data.repository.ThemeRepositoryImpl
import com.example.playlistmaker.data.repository.TracksMapper
import com.example.playlistmaker.data.repository.TracksRepositoryImpl
import com.example.playlistmaker.domain.api.HistoryInteractor
import com.example.playlistmaker.domain.api.ModeInteractor
import com.example.playlistmaker.domain.api.PlayerInteractor
import com.example.playlistmaker.domain.api.TracksInteractor
import com.example.playlistmaker.domain.impl.ClickDebounceUseCaseImpl
import com.example.playlistmaker.domain.impl.HistoryInteractorImpl
import com.example.playlistmaker.domain.impl.InputDebounceUseCaseImpl
import com.example.playlistmaker.domain.impl.ModeInteractorImpl
import com.example.playlistmaker.domain.impl.PlayerInteractorImpl
import com.example.playlistmaker.domain.impl.TracksInteractorImpl
import com.example.playlistmaker.domain.repository.LoopRepository
import com.example.playlistmaker.domain.repository.PlayerRepository
import com.example.playlistmaker.domain.repository.PreferencesRepository
import com.example.playlistmaker.domain.repository.ThemeRepository
import com.example.playlistmaker.domain.repository.TracksRepository
import com.example.playlistmaker.domain.use_case.ClickDebounceUseCase
import com.example.playlistmaker.domain.use_case.InputDebounceUseCase

object Creator {
    private fun getTracksRepository(): TracksRepository {
        return TracksRepositoryImpl(TracksRetrofitNetworkClient(), getTracksMapper())
    }

    fun provideTracksInteractor(): TracksInteractor {
        return TracksInteractorImpl(getTracksRepository())
    }

    private fun getTracksMapper(): TracksMapper {
        return TracksMapperImpl()
    }
    private fun getPreferensiesRepository(context: Context): PreferencesRepository {
        return PreferencesRepositoryImpl(context, getTracksMapper())
    }

    private fun getThemeRepository(): ThemeRepository {
        return ThemeRepositoryImpl()
    }

    fun provideHistoryInteractor(context: Context): HistoryInteractor {
        return HistoryInteractorImpl(getPreferensiesRepository(context), 10)
    }

    fun provideModeInteractor(context: Context): ModeInteractor {
        return ModeInteractorImpl(getPreferensiesRepository(context),getThemeRepository())
    }

    private fun getPlayerRepository(url: String): PlayerRepository {
        return PlayerRepositoryImpl(url)
    }

    fun providePlayerInteractor(url: String, onState:(Int) -> Unit): PlayerInteractor {
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
}