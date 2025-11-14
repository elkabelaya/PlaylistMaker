package com.example.playlistmaker.domain.api

import com.example.playlistmaker.domain.model.Track
import com.example.playlistmaker.domain.model.Tracks

interface SearchInteractor {
    var tracks: Tracks
    var history: Tracks
    fun changeQuery(query: CharSequence)
    fun changeFocus(isFocused: Boolean)
    fun clearQuery()
    fun select(track: Track)
    fun clearHistory()
    fun refresh()

    companion object {
        const val STATE_DEFAULT = 0
        const val STATE_ENTER = 1
        const val STATE_HISTORY = 2
        const val STATE_LOADING = 3
        const val STATE_RESULT = 4
        const val STATE_EMPTY = 5
        const val STATE_ERROR = 6

    }
}