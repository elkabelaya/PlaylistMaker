package com.example.playlistmaker.newplaylist.di

import com.example.playlistmaker.common.domain.repository.LocalStorage
import com.example.playlistmaker.newplaylist.domain.api.NewPlayListInteractor
import com.example.playlistmaker.newplaylist.domain.impl.NewPlayListInteractorImpl
import org.koin.core.qualifier.named
import org.koin.dsl.module

val newplaylistDomainModule = module {
    factory<NewPlayListInteractor> { NewPlayListInteractorImpl(get(), get(named(LocalStorage.PLAYLIST_COVERS_FOLDER))) }
}
