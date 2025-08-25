package com.example.playlistmaker.network

import NullableTypeAdapterFactory
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


object Network {
    private val retrofit: Retrofit
    val  itunesService: ItunesApi by lazy {
        retrofit.create(ItunesApi::class.java)
    }

    init {
        val httpClientBuilder = OkHttpClient.Builder()
        httpClientBuilder.addInterceptor(SuccessCheckInterceptor())
        val client: OkHttpClient = httpClientBuilder.build()
        val responseGson: Gson = GsonBuilder()
            .registerTypeAdapterFactory(NullableTypeAdapterFactory())
            .create()

        retrofit = Retrofit.Builder()
            .baseUrl("https://itunes.apple.com")
            .client(client)
            .addConverterFactory(GsonConverterFactory.create(responseGson))
            .build()
    }
}