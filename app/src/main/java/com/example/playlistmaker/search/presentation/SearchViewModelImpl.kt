package com.example.playlistmaker.search.presentation

import androidx.annotation.OptIn
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.media3.common.util.Log
import androidx.media3.common.util.UnstableApi
import com.example.playlistmaker.common.domain.model.Track
import com.example.playlistmaker.common.domain.use_case.ClickDebounceUseCase
import com.example.playlistmaker.search.domain.api.SearchInteractor
import com.example.playlistmaker.search.domain.api.SearchNavigatorInteractor
import com.example.playlistmaker.search.domain.model.SearchState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class SearchViewModelImpl(
    val searchInteractor: SearchInteractor,
    val navigatorInteractor: SearchNavigatorInteractor,
    val clickDebounceUseCase: ClickDebounceUseCase
): SearchViewModel() {
    private val stateLiveData= MutableLiveData<SearchState>(SearchState.Default)
    override fun observeState(): LiveData<SearchState> = stateLiveData

    private val _state = MutableStateFlow<SearchState>(SearchState.Default)
    override var state: StateFlow<SearchState> = _state.asStateFlow()

    init {
        searchInteractor.onState { state ->
            stateLiveData.postValue( state)
            _state.value = state
        }
    }

    @OptIn(UnstableApi::class)
    override fun changeQuery(query: String) {
        Log.d("Search query", query)
        this.query.value = query
        viewModelScope.launch(Dispatchers.IO) {
            searchInteractor.changeQuery(query)
        }
    }

    override fun changeFocus(isFocused: Boolean){
        searchInteractor.changeFocus(isFocused)
    }

    override fun clearQuery(){
        query.value = ""
        searchInteractor.clearQuery()
    }
    override fun select(track: Track){
        if (clickDebounceUseCase.canClickDebounced()) {
            searchInteractor.select(track)
            navigatorInteractor.navigateTo(track)
        }
    }

    override fun clearHistory(){
        searchInteractor.clearHistory()
    }

    override fun refresh(){
        viewModelScope.launch(Dispatchers.IO) {
            searchInteractor.refresh()
        }
    }
}