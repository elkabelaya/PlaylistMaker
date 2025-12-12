package com.example.playlistmaker.media.domain.impl

import com.example.playlistmaker.common.domain.model.ErrorState
import com.example.playlistmaker.media.domain.api.MediaFavoritesInteractor
import com.example.playlistmaker.media.domain.model.MediaFavoritesState
import com.example.playlistmaker.media.domain.repository.MediaFavoritesErrorRepository
import com.example.playlistmaker.media.domain.repository.MediaFavoritesRepository

class MediaFavoritesInteractorImpl(
    val repository: MediaFavoritesRepository,
    val errorRepository: MediaFavoritesErrorRepository
): MediaFavoritesInteractor {
    private var sendState:((MediaFavoritesState)-> Unit)? = null
    private var state: MediaFavoritesState = MediaFavoritesState.Loading
        set(value) {
            field = value
            sendState?.let {
                it(value)
            }
        }

    override fun getFracks() {
        state = MediaFavoritesState.Error(ErrorState.Empty(errorRepository.getErrorText()))
    }

    override fun onState(state: (MediaFavoritesState) -> Unit) {
        sendState = state
    }
}