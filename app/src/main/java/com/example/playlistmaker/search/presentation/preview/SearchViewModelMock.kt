package com.example.playlistmaker.search.presentation.preview

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.tooling.preview.datasource.LoremIpsum
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.playlistmaker.common.domain.model.Track
import com.example.playlistmaker.search.domain.model.SearchState
import com.example.playlistmaker.search.presentation.SearchViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class SearchViewModelMock(
    mockState: SearchState,
    query: String =""
): SearchViewModel() {
    private val stateLiveData= MutableLiveData<SearchState>(SearchState.Default)
    override fun observeState(): LiveData<SearchState> = stateLiveData

    private val _state = MutableStateFlow<SearchState>(SearchState.Default)
    override var state: StateFlow<SearchState> = _state.asStateFlow()

    init {
        _state.value = mockState
        this.query.value = query
    }

    override fun changeQuery(query: String) {

    }

    override fun changeFocus(isFocused: Boolean){

    }

    override fun clearQuery(){

    }
    override fun select(track: Track){

    }

    override fun clearHistory(){

    }

    override fun refresh(){

    }
}