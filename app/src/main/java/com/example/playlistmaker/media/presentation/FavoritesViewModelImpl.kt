package com.example.playlistmaker.media.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.playlistmaker.common.domain.model.Track
import com.example.playlistmaker.media.domain.api.MediaFavoritesInteractor
import com.example.playlistmaker.media.domain.model.MediaFavoritesState

class FavoritesViewModelImpl(
    val interactor: MediaFavoritesInteractor
): FavoritesViewModel() {
    private val stateLiveData = MutableLiveData<MediaFavoritesState>(MediaFavoritesState.Loading)
    override fun observeState(): LiveData<MediaFavoritesState> = stateLiveData

    init {
        interactor.onState {
            stateLiveData.postValue(it)
        }
        interactor.getFracks()
    }
    override fun select(track: Track) {
        //do nothing by now
    }
}