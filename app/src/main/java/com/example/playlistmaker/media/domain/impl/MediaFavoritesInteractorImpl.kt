package com.example.playlistmaker.media.domain.impl

import com.example.playlistmaker.common.domain.model.ErrorState
import com.example.playlistmaker.common.domain.repository.FavoriteTracksRepository
import com.example.playlistmaker.media.domain.api.MediaFavoritesInteractor
import com.example.playlistmaker.media.domain.model.MediaFavoritesState
import com.example.playlistmaker.media.domain.repository.MediaFavoritesErrorRepository

class MediaFavoritesInteractorImpl(
    val repository: FavoriteTracksRepository,
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

    override suspend fun getFracks() {
        repository.get()
            .collect { tracks ->
                state = if (tracks.isEmpty()) {
                    MediaFavoritesState.Error(ErrorState.Empty(errorRepository.getErrorText()))
                } else {
                    MediaFavoritesState.Data(tracks)
                }
            }
    }

    override fun onState(state: (MediaFavoritesState) -> Unit) {
        sendState = state
    }
}