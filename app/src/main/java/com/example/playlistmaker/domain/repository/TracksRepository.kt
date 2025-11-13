package com.example.playlistmaker.domain.repository

import Resource
import com.example.playlistmaker.domain.model.Tracks

interface TracksRepository {
    fun getTracks(query: String): Resource<Tracks>
}
