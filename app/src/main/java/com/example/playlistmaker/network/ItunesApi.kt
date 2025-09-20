package com.example.playlistmaker.network

import com.example.playlistmaker.models.Tracks
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ItunesApi {
    @GET("/search?entity=song")
    fun findTrack(@Query("term") text: String): Call<Tracks>
}