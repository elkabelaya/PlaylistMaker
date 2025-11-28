package com.example.playlistmaker.search.data.repository

import com.example.playlistmaker.common.data.model.NetworkResponse
import com.example.playlistmaker.common.data.model.TracksDto

interface TracksNetworkClient {
    fun getTracks(query: String): NetworkResponse<TracksDto?>
}