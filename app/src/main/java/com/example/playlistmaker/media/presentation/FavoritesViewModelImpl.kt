package com.example.playlistmaker.media.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.playlistmaker.common.domain.model.Track
import com.example.playlistmaker.common.domain.use_case.ClickDebounceUseCase
import com.example.playlistmaker.media.domain.api.MediaFavoritesInteractor
import com.example.playlistmaker.media.domain.api.MediaNavigatorInteractor
import com.example.playlistmaker.media.domain.model.MediaFavoritesState
import com.example.playlistmaker.search.domain.api.SearchNavigatorInteractor
import kotlinx.coroutines.launch

class FavoritesViewModelImpl(
    val interactor: MediaFavoritesInteractor,
    val navigatorInteractor: MediaNavigatorInteractor,
    val clickDebounceUseCase: ClickDebounceUseCase
): FavoritesViewModel() {
    private val stateLiveData = MutableLiveData<MediaFavoritesState>(MediaFavoritesState.Loading)
    override fun observeState(): LiveData<MediaFavoritesState> = stateLiveData

    init {
        interactor.onState {
            stateLiveData.postValue(it)
        }
        viewModelScope.launch {
            interactor.getFracks()
        }
    }
    override fun select(track: Track) {
        if (clickDebounceUseCase.canClickDebounced()) {
            navigatorInteractor.navigateTo(track)
        }
    }
}