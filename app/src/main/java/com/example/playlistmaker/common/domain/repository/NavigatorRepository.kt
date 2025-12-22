package com.example.playlistmaker.common.domain.repository
import com.example.playlistmaker.common.domain.model.Navigation

interface NavigatorRepository {
    fun navigateTo(navigation: Navigation)
}