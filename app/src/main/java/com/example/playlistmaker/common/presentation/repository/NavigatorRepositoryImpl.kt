package com.example.playlistmaker.common.presentation.repository

import androidx.navigation.NavController
import com.example.playlistmaker.common.domain.model.Navigation
import com.example.playlistmaker.common.domain.repository.NavigatorRepository

interface NavControllerKeeper {
    fun setup(navController: NavController)
}
class NavigatorRepositoryImpl(): NavigatorRepository, NavControllerKeeper {
    private var navController: NavController? = null

    override fun navigateTo(navigation: Navigation) {
        navController?.navigate(
            navigation.action,
            navigation.args
        )
    }
    override fun setup(navController: NavController) {
        this.navController = navController
    }
}