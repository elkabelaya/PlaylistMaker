package com.example.playlistmaker.data.network

import com.example.playlistmaker.data.model.TracksDto
import com.example.playlistmaker.data.model.NetworkResponse
import com.example.playlistmaker.data.repository.TracksNetworkClient

class TracksRetrofitNetworkClient : TracksNetworkClient {

    override fun getTracks(query: String): NetworkResponse<TracksDto?> {
        return try {
            val response = RetrofitClient.itunesService.getTracks(query).execute()
            NetworkResponse(response.body(), response.code().toString())
        } catch (ex: Exception) {
            NetworkResponse(null, ex.message ?: "-1")
        }
    }
}