package com.example.playlistmaker.media.di

import com.example.playlistmaker.media.data.repository.MediaPlaylistsRepositoryImpl
import com.example.playlistmaker.media.domain.repository.MediaPlaylistsRepository
import org.koin.dsl.module

val mediaDataModule = module {
    single<MediaPlaylistsRepository>{ MediaPlaylistsRepositoryImpl() }
}
