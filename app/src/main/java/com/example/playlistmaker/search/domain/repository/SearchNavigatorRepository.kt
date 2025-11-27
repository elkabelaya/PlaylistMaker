package com.example.playlistmaker.search.domain.repository

import com.example.playlistmaker.domain.model.Email
import com.example.playlistmaker.domain.model.Navigation
import com.example.playlistmaker.domain.model.Track
import com.example.playlistmaker.player.presentation.PlayerActivity

interface SearchNavigatorRepository {
    fun getPlayerActivity(item: Track): Navigation
}