package com.example.playlistmaker.player.di

import com.example.playlistmaker.common.domain.repository.Permission
import com.example.playlistmaker.common.domain.repository.PermissionsRepository
import com.example.playlistmaker.player.domain.repository.PlayerNavigatorRepository
import com.example.playlistmaker.player.data.repository.PlayerRepositoryImpl
import com.example.playlistmaker.player.data.repository.ServicePlayerRepositoryImpl
import com.example.playlistmaker.player.domain.repository.PlayerRepository
import com.example.playlistmaker.player.presentation.repository.PlayerNavigatorRepositoryImpl
import org.koin.dsl.module

val playerDataModule = module {
    factory<PlayerRepository> {
        var permissionsRepository: PermissionsRepository = get()
        //если пользователь выдал разрешение - используем сервис с уведомлениями,
        //если нет - старую реализации плеера без сервиса
        //четкого требования, как поступать без разрешения, в ТЗ нет,
        //а этот вариант показался мне приемлимым
        if (permissionsRepository.get(Permission.NOTIFICATIONS)) {
            return@factory ServicePlayerRepositoryImpl(get())
        }
        return@factory PlayerRepositoryImpl()

    }
    factory<PlayerNavigatorRepository> { PlayerNavigatorRepositoryImpl() }
}
