package com.example.playlistmaker.search.domain.repository

import com.example.playlistmaker.domain.model.Resource
import com.example.playlistmaker.domain.model.Tracks

interface TracksRepository {
    fun getTracks(query: String): Resource<Tracks>
}