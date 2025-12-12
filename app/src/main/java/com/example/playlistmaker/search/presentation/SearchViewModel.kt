package com.example.playlistmaker.search.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.playlistmaker.common.domain.model.Track
import com.example.playlistmaker.common.presentation.StateFullViewModel
import com.example.playlistmaker.search.domain.model.SearchState

abstract class SearchViewModel: StateFullViewModel<SearchState>, ViewModel() {
    abstract fun changeQuery(query: CharSequence)
    abstract fun changeFocus(isFocused: Boolean)
    abstract fun clearQuery()
    abstract fun select(track: Track)
    abstract fun clearHistory()
    abstract fun refresh()
}