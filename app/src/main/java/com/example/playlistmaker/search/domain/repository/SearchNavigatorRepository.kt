package com.example.playlistmaker.search.domain.repository

import com.example.playlistmaker.common.domain.model.Navigation
import com.example.playlistmaker.common.domain.model.Track

interface SearchNavigatorRepository {
    fun getPlayer(item: Track): Navigation
}