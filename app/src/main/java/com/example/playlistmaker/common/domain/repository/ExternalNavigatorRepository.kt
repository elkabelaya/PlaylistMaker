package com.example.playlistmaker.common.domain.repository
import com.example.playlistmaker.common.domain.model.Email

interface ExternalNavigatorRepository {
    fun share(data: String)
    fun openWeb(link: String)
    fun openEmail(email: Email)
}