package com.example.playlistmaker.domain.repository

import com.example.playlistmaker.domain.model.Tracks

interface PreferencesRepository {
    var isNightMode: Boolean
    var searchHistory: Tracks
}