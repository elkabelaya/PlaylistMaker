package com.example.playlistmaker.editplaylist.di

import com.example.playlistmaker.common.di.CommonDiNames
import com.example.playlistmaker.common.domain.model.Playlist
import com.example.playlistmaker.editplaylist.domain.api.EditPlayListInteractor
import com.example.playlistmaker.editplaylist.domain.impl.EditPlayListInteractorImpl
import org.koin.core.qualifier.named
import org.koin.dsl.module

val editplaylistDomainModule = module {
    factory<EditPlayListInteractor> { params ->
        EditPlayListInteractorImpl(get(), get(named(CommonDiNames.PLAYLIST_COVERS_FOLDER_NAME))) }
}
