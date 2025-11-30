package com.example.playlistmaker.search.data.network

import com.example.playlistmaker.common.data.model.NetworkResponse
import com.example.playlistmaker.common.data.model.TracksDto
import com.example.playlistmaker.common.data.network.ItunesApi
import com.example.playlistmaker.common.data.network.RetrofitClient
import com.example.playlistmaker.search.data.repository.TracksNetworkClient

class TracksRetrofitNetworkClient(val itunesApi: ItunesApi) : TracksNetworkClient {

    override fun getTracks(query: String): NetworkResponse<TracksDto?> {
        return try {
            val response = itunesApi.getTracks(query).execute()
            NetworkResponse(response.body(), response.code().toString())
        } catch (ex: Exception) {
            NetworkResponse(null, ex.message ?: "-1")
        }
    }
}