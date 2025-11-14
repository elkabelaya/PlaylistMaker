package com.example.playlistmaker.data.model

data class NetworkResponse<T>(val data: T, val code: String)