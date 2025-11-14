package com.example.playlistmaker.data.repository

import com.example.playlistmaker.data.model.TracksDto
import com.example.playlistmaker.data.model.NetworkResponse

interface TracksNetworkClient {
    fun getTracks(query: String): NetworkResponse<TracksDto?>
}