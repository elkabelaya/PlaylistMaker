package com.example.playlistmaker.domain.repository
import com.example.playlistmaker.domain.model.Email
import com.example.playlistmaker.domain.model.Navigation

interface NavigatorRepository {
    fun navigateTo(navigation: Navigation)
    fun shareLink(link: String)
    fun openWeb(link: String)
    fun openEmail(email: Email)
}