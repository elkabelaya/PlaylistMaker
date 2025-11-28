package com.example.playlistmaker.search.presentation

import androidx.lifecycle.LiveData
import com.example.playlistmaker.common.domain.model.Track
import com.example.playlistmaker.search.domain.api.SearchState

interface SearchViewModel {
    fun observeState(): LiveData<SearchState>
    fun changeQuery(query: CharSequence)
    fun changeFocus(isFocused: Boolean)
    fun clearQuery()
    fun select(track: Track)
    fun clearHistory()
    fun refresh()
}