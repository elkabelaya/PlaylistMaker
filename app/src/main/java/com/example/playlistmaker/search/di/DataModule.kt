package com.example.playlistmaker.search.di

import com.example.playlistmaker.search.data.network.TracksRetrofitNetworkClient
import com.example.playlistmaker.search.data.repository.TracksNetworkClient
import com.example.playlistmaker.search.data.repository.TracksRepositoryImpl
import com.example.playlistmaker.search.domain.repository.TracksRepository
import org.koin.dsl.module

val searchDataModule = module {
    factory<TracksNetworkClient> { TracksRetrofitNetworkClient(get())}
    factory<TracksRepository> { TracksRepositoryImpl(get(), get()) }
}
