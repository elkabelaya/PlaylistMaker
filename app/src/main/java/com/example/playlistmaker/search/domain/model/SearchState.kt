package com.example.playlistmaker.search.domain.model

import com.example.playlistmaker.common.domain.model.ErrorState
import com.example.playlistmaker.common.domain.model.Tracks

sealed class SearchState {
    data object Default: SearchState()
    data object Enter: SearchState()
    data class History(val tracks: Tracks): SearchState()
    data object Loading: SearchState()
    data class Result(val tracks: Tracks): SearchState()
    data class Error(val errorState: ErrorState): SearchState()
}