package com.example.playlistmaker.search.presentation.repository

import android.content.Context
import com.example.playlistmaker.R
import com.example.playlistmaker.search.domain.repository.SearchErrorRepository

class SearchErrorRepositoryImpl(val context: Context): SearchErrorRepository {
    override fun getEmptyText(): String {
        return context.getString(R.string.error_not_found)
    }
    override fun getWifiText(): String {
        return context.getString(R.string.error_wifi)
    }
}