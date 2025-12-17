package com.example.playlistmaker.search.domain.repository

interface SearchErrorRepository {
    fun getEmptyText(): String
    fun getWifiText(): String
}