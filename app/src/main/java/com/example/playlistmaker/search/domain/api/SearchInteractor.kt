package com.example.playlistmaker.search.domain.api

import com.example.playlistmaker.common.domain.api.StateFullInteractor
import com.example.playlistmaker.common.domain.model.Track
import com.example.playlistmaker.search.domain.model.SearchState

interface SearchInteractor:StateFullInteractor<SearchState> {
    fun changeQuery(query: CharSequence)
    fun changeFocus(isFocused: Boolean)
    fun clearQuery()
    fun select(track: Track)
    fun clearHistory()
    fun refresh()
}