package com.example.playlistmaker.search.presentation

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.playlistmaker.creator.Creator
import com.example.playlistmaker.common.domain.model.Track
import com.example.playlistmaker.common.domain.use_case.ClickDebounceUseCase
import com.example.playlistmaker.search.domain.api.SearchInteractor
import com.example.playlistmaker.search.domain.api.SearchNavigatorInteractor
import com.example.playlistmaker.search.domain.api.SearchState

class SearchViewModelImpl(
    val searchInteractor: SearchInteractor,
    val navigatorInteractor: SearchNavigatorInteractor,
    val clickDebounceUseCase: ClickDebounceUseCase
    ): SearchViewModel, ViewModel() {
    private val stateLiveData= MutableLiveData<SearchState>(SearchState.Default)
    override fun observeState(): LiveData<SearchState> = stateLiveData
    init {
        searchInteractor.onState { state ->
            stateLiveData.postValue( state)
        }
    }
    override fun changeQuery(query: CharSequence) {
        searchInteractor.changeQuery(query)
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
        searchInteractor.refresh()
    }
    companion object {
        fun getFactory( searchInteractor: SearchInteractor,
                        navigatorInteractor: SearchNavigatorInteractor,
                        clickDebounceUseCase: ClickDebounceUseCase): ViewModelProvider.Factory = viewModelFactory {
            initializer {
                SearchViewModelImpl(
                    searchInteractor,
                    navigatorInteractor,
                    clickDebounceUseCase
                )
            }
        }
    }
}