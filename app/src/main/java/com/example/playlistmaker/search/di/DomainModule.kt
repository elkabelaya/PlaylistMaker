package com.example.playlistmaker.search.di

import com.example.playlistmaker.search.domain.api.SearchInteractor
import com.example.playlistmaker.search.domain.api.SearchNavigatorInteractor
import com.example.playlistmaker.search.domain.impl.GetTracksUseCaseImpl
import com.example.playlistmaker.search.domain.impl.HistoryUseCaseImpl
import com.example.playlistmaker.search.domain.impl.InputDebounceUseCaseImpl
import com.example.playlistmaker.search.domain.impl.SearchInteractorImpl
import com.example.playlistmaker.search.domain.impl.SearchNavigatorInteractorImpl
import com.example.playlistmaker.search.domain.use_case.GetTracksUseCase
import com.example.playlistmaker.search.domain.use_case.HistoryUseCase
import com.example.playlistmaker.search.domain.use_case.InputDebounceUseCase
import org.koin.dsl.module

val searchDomainModule = module {
    factory<GetTracksUseCase> { GetTracksUseCaseImpl(get()) }
    factory<InputDebounceUseCase>{ InputDebounceUseCaseImpl(get()) }
    factory<SearchInteractor>{SearchInteractorImpl(get(), get(), get(), get(), get())}
    factory<HistoryUseCase>{ HistoryUseCaseImpl(get(), 10) }
    factory<SearchNavigatorInteractor> {params ->
        SearchNavigatorInteractorImpl(get{params}, get())
    }
}
