package com.example.playlistmaker.search.presentation

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.playlistmaker.common.domain.model.Track
import com.example.playlistmaker.common.presentation.ComposeStateFullViewModel
import com.example.playlistmaker.common.presentation.StateFullViewModel
import com.example.playlistmaker.search.domain.model.SearchState
import kotlinx.coroutines.flow.StateFlow

abstract class SearchViewModel: StateFullViewModel<SearchState>,
    ComposeStateFullViewModel<SearchState>, ViewModel() {
    var query = mutableStateOf("")
    abstract fun changeQuery(query: String)
    abstract fun changeFocus(isFocused: Boolean)
    abstract fun clearQuery()
    abstract fun select(track: Track)
    abstract fun clearHistory()
    abstract fun refresh()
}