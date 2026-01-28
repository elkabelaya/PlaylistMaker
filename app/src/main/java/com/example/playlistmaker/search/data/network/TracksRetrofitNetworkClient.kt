package com.example.playlistmaker.search.data.network

import com.example.playlistmaker.common.data.model.NetworkResponse
import com.example.playlistmaker.common.data.model.TracksDto
import com.example.playlistmaker.common.data.network.ItunesApi
import com.example.playlistmaker.common.data.network.RetrofitClient
import com.example.playlistmaker.search.data.repository.TracksNetworkClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class TracksRetrofitNetworkClient(val itunesApi: ItunesApi) : TracksNetworkClient {

    override suspend fun getTracks(query: String): NetworkResponse<TracksDto?> {
        return withContext(Dispatchers.IO) {
            try {
                val response = itunesApi.getTracks(query)
                NetworkResponse(response.body(), response.code().toString())
            } catch (ex: Exception) {
                NetworkResponse(null, ex.message ?: "-1")
            }
        }
    }
}