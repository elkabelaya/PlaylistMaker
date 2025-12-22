package com.example.playlistmaker.common.data.repository

import androidx.navigation.NavController
import com.example.playlistmaker.common.domain.model.Navigation
import com.example.playlistmaker.common.domain.repository.NavigatorRepository

class NavigatorRepositoryImpl(
    private val navController: NavController
): NavigatorRepository {
    override fun navigateTo(navigation: Navigation) {
        navController.navigate(
            navigation.action,
            navigation.args)
    }
}