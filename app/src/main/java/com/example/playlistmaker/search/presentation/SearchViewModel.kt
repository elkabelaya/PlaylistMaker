package com.example.playlistmaker.search.presentation

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.playlistmaker.creator.Creator
import com.example.playlistmaker.domain.model.Track
import com.example.playlistmaker.search.domain.api.SearchInteractor
import com.example.playlistmaker.search.domain.api.SearchNavigatorInteractor
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

class SearchViewModelImpl(val context: Context): SearchViewModel, ViewModel() {
    private val stateLiveData= MutableLiveData<SearchState>(SearchState.Default)
    override fun observeState(): LiveData<SearchState> = stateLiveData

    private lateinit var searchInteractor: SearchInteractor
    private lateinit var navigatorInteractor: SearchNavigatorInteractor
    private var clickDebounceUseCase = Creator.provideClickDebounceUseCase()
    init {
        searchInteractor = Creator.provideSearchInteractor(context) { state ->
            stateLiveData.postValue(state)
        }
        navigatorInteractor = Creator.provideSearchNavigatorInteractor(context)
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
        fun getFactory(context: Context): ViewModelProvider.Factory = viewModelFactory {
            initializer {
                SearchViewModelImpl(context)
            }
        }

        const val INTENT_KEY = "INTENT_KEY"
        private const val PLAYER_DEBOUNCE_DELAY = 300L
    }
}