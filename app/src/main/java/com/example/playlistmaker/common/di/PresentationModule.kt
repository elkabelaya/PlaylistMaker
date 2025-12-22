package com.example.playlistmaker.common.di
import com.example.playlistmaker.common.data.repository.ExternalNavigatorRepositoryImpl
import com.example.playlistmaker.common.data.repository.NavigatorRepositoryImpl
import com.example.playlistmaker.common.domain.repository.ExternalNavigatorRepository
import com.example.playlistmaker.common.domain.repository.NavigatorRepository
import org.koin.dsl.module

val commonPresentationModule = module {
    factory<NavigatorRepository> { params ->
        NavigatorRepositoryImpl(params.get())
    }
    factory<ExternalNavigatorRepository> {
        ExternalNavigatorRepositoryImpl( get())
    }
}
