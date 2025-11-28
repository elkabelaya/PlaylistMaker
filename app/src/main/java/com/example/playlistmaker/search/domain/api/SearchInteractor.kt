package com.example.playlistmaker.search.domain.api

import com.example.playlistmaker.common.domain.model.Track

interface SearchInteractor {
    fun onState(state: (SearchState) -> Unit)
    fun changeQuery(query: CharSequence)
    fun changeFocus(isFocused: Boolean)
    fun clearQuery()
    fun select(track: Track)
    fun clearHistory()
    fun refresh()
}