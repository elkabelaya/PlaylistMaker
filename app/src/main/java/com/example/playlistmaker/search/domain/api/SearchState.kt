package com.example.playlistmaker.search.domain.api

import com.example.playlistmaker.common.domain.model.Tracks

sealed class SearchState {
    data object Default: SearchState()
    data object Enter: SearchState()
    data class History(val tracks: Tracks): SearchState()
    data object Loading: SearchState()
    data class Result(val tracks: Tracks): SearchState()
    data object Empty: SearchState()
    data object Error: SearchState()
}