package com.example.playlistmaker.search.domain.repository

import com.example.playlistmaker.common.domain.model.Email
import com.example.playlistmaker.common.domain.model.Navigation
import com.example.playlistmaker.common.domain.model.Track
import com.example.playlistmaker.player.presentation.PlayerActivity

interface SearchErrorRepository {
    fun getEmptyText(): String
    fun getWifiText(): String
}