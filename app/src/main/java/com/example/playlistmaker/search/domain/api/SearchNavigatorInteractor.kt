package com.example.playlistmaker.search.domain.api

import com.example.playlistmaker.domain.model.Track

interface SearchNavigatorInteractor {
    fun navigateTo(track: Track)
}