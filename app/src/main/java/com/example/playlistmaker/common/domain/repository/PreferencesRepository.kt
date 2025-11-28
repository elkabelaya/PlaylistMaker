package com.example.playlistmaker.common.domain.repository

import com.example.playlistmaker.common.domain.model.Tracks

interface PreferencesRepository {
    var isNightMode: Boolean
    var searchHistory: Tracks
}