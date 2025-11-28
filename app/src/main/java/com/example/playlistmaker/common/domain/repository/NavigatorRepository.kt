package com.example.playlistmaker.common.domain.repository
import com.example.playlistmaker.common.domain.model.Email
import com.example.playlistmaker.common.domain.model.Navigation

interface NavigatorRepository {
    fun navigateTo(navigation: Navigation)
    fun shareLink(link: String)
    fun openWeb(link: String)
    fun openEmail(email: Email)
}