package com.example.playlistmaker.common.data.network

import com.example.playlistmaker.common.data.model.TracksDto
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ItunesApi {
    @GET("/search?entity=song")
    suspend fun getTracks(@Query("term") text: String): Response<TracksDto>
}