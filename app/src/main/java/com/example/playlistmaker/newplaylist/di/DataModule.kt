package com.example.playlistmaker.newplaylist.di

import com.example.playlistmaker.media.data.repository.MediaPlaylistsRepositoryImpl
import com.example.playlistmaker.media.domain.repository.MediaPlaylistsRepository
import org.koin.dsl.module

val newplaylistDataModule = module {
    single<MediaPlaylistsRepository>{ MediaPlaylistsRepositoryImpl() }
}
