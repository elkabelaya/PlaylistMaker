package com.example.playlistmaker.domain.repository
import java.io.Serializable

interface NavigatorRepository {
    fun navigateTo(activity: Class<*>, data: Serializable)
    fun shareLink(link: String)
    fun openWeb(link: String)
    fun openEmail(email: String)
}