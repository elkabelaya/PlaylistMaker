package com.example.playlistmaker.common.di

import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import com.example.playlistmaker.common.data.mapper.TracksMapperImpl
import com.example.playlistmaker.common.data.network.ItunesApi
import com.example.playlistmaker.common.data.network.RetrofitClient
import com.example.playlistmaker.common.data.repository.CoroutinesLoopRepositoryImpl
import com.example.playlistmaker.common.data.repository.PreferencesRepositoryImpl
import com.example.playlistmaker.common.data.repository.ThemeRepositoryImpl
import com.example.playlistmaker.common.data.repository.TracksMapper
import com.example.playlistmaker.common.domain.repository.LoopRepository
import com.example.playlistmaker.common.domain.repository.PreferencesRepository
import com.example.playlistmaker.common.domain.repository.ThemeRepository
import org.koin.dsl.module

val commonDataModule = module {
    single<ItunesApi>{ RetrofitClient.itunesService }
    single<TracksMapper> { TracksMapperImpl() }
    factory<LoopRepository>{ CoroutinesLoopRepositoryImpl() }
    single<PreferencesRepository>{ PreferencesRepositoryImpl(get(), get()) }
    single<ThemeRepository>{ ThemeRepositoryImpl() }
    factory { (owner: NavHostFragment) -> owner.findNavController()}
}
