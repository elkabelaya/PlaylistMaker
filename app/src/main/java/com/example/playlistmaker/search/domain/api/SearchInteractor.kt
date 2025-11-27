package com.example.playlistmaker.search.domain.api

import com.example.playlistmaker.domain.model.Track
import com.example.playlistmaker.domain.model.Tracks

interface SearchInteractor {
    val onState:(state: SearchState) -> Unit
    fun changeQuery(query: CharSequence)
    fun changeFocus(isFocused: Boolean)
    fun clearQuery()
    fun select(track: Track)
    fun clearHistory()
    fun refresh()
}