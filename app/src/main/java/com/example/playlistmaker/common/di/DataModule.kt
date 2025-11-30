package com.example.playlistmaker.common.di

import com.example.playlistmaker.common.data.mapper.TracksMapperImpl
import com.example.playlistmaker.common.data.network.ItunesApi
import com.example.playlistmaker.common.data.network.RetrofitClient
import com.example.playlistmaker.common.data.repository.LoopRepositoryImpl
import com.example.playlistmaker.common.data.repository.NavigatorRepositoryImpl
import com.example.playlistmaker.common.data.repository.PreferencesRepositoryImpl
import com.example.playlistmaker.common.data.repository.ThemeRepositoryImpl
import com.example.playlistmaker.common.data.repository.TracksMapper
import com.example.playlistmaker.common.domain.repository.LoopRepository
import com.example.playlistmaker.common.domain.repository.NavigatorRepository
import com.example.playlistmaker.common.domain.repository.PreferencesRepository
import com.example.playlistmaker.common.domain.repository.ThemeRepository
import org.koin.dsl.module

val commonDataModule = module {
    single<ItunesApi>{ RetrofitClient.itunesService }
    factory<TracksMapper> { TracksMapperImpl() }
    factory<LoopRepository>{ LoopRepositoryImpl() }
    factory<PreferencesRepository>{ PreferencesRepositoryImpl(get(), get()) }
    factory<NavigatorRepository>{ NavigatorRepositoryImpl(get()) }
    factory<ThemeRepository>{ ThemeRepositoryImpl() }
}
