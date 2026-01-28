package com.example.playlistmaker.search.presentation

import androidx.core.R
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.playlistmaker.common.domain.model.Track
import com.example.playlistmaker.common.domain.use_case.ClickDebounceUseCase
import com.example.playlistmaker.search.domain.api.SearchInteractor
import com.example.playlistmaker.search.domain.api.SearchNavigatorInteractor
import com.example.playlistmaker.search.domain.model.SearchState
import kotlinx.coroutines.launch

class SearchViewModelImpl(
    val searchInteractor: SearchInteractor,
    val navigatorInteractor: SearchNavigatorInteractor,
    val clickDebounceUseCase: ClickDebounceUseCase
    ): SearchViewModel() {
    private val stateLiveData= MutableLiveData<SearchState>(SearchState.Default)
    override fun observeState(): LiveData<SearchState> = stateLiveData

    init {
        searchInteractor.onState { state ->
            stateLiveData.postValue( state)
        }
    }

    override fun changeQuery(query: CharSequence) {
        viewModelScope.launch {
            searchInteractor.changeQuery(query)
        }
    }

    override fun changeFocus(isFocused: Boolean){
        searchInteractor.changeFocus(isFocused)
    }

    override fun clearQuery(){
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
        viewModelScope.launch {
            searchInteractor.refresh()
        }
    }
}