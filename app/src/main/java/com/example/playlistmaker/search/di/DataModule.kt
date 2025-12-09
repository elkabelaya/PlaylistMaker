package com.example.playlistmaker.search.di

import com.example.playlistmaker.search.data.network.TracksRetrofitNetworkClient
import com.example.playlistmaker.search.data.repository.SearchErrorRepositoryImpl
import com.example.playlistmaker.search.data.repository.TracksNetworkClient
import com.example.playlistmaker.search.data.repository.TracksRepositoryImpl
import com.example.playlistmaker.search.domain.repository.SearchErrorRepository
import com.example.playlistmaker.search.domain.repository.TracksRepository
import org.koin.dsl.module

val searchDataModule = module {
    single<TracksNetworkClient> { TracksRetrofitNetworkClient(get())}
    factory<TracksRepository> { TracksRepositoryImpl(get(), get()) }
    single<SearchErrorRepository> { SearchErrorRepositoryImpl(get())}
}
