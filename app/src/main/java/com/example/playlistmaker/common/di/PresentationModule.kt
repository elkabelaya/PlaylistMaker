package com.example.playlistmaker.common.di
import com.example.playlistmaker.common.presentation.repository.ExternalNavigatorRepositoryImpl
import com.example.playlistmaker.common.domain.repository.ExternalNavigatorRepository
import com.example.playlistmaker.common.domain.repository.NavigatorRepository
import com.example.playlistmaker.common.presentation.repository.NavControllerKeeper
import com.example.playlistmaker.common.presentation.repository.NavigatorRepositoryImpl
import org.koin.dsl.module

val commonPresentationModule = module {

    single<NavigatorRepositoryImpl> {
        NavigatorRepositoryImpl()
    }

    single<NavigatorRepository> {
        val navigator: NavigatorRepositoryImpl = get()
        return@single navigator
    }

    single<NavControllerKeeper> {
        val navKeeper: NavigatorRepositoryImpl = get()
        return@single navKeeper
    }

    factory<ExternalNavigatorRepository> {
        ExternalNavigatorRepositoryImpl( get())
    }
}
